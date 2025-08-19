package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.clients.OrderClient;
import ru.practicum.dto.warehouse.*;
import ru.practicum.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.practicum.model.OrderBooking;
import ru.practicum.model.WarehouseInventory;
import ru.practicum.repository.OrderBookingRepository;
import ru.practicum.repository.WarehouseInventoryRepository;
import ru.practicum.repository.WarehouseProductRepository;
import ru.practicum.dto.shoppingCart.ShoppingCartDto;
import ru.practicum.exception.NotFoundException;
import ru.practicum.model.WarehouseProduct;
import ru.practicum.utils.WarehouseMapper;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseProductRepository warehouseProductRepository;
    private final WarehouseInventoryRepository warehouseInventoryRepository;
    private final OrderBookingRepository orderBookingRepository;

    private final OrderClient orderClient;

    private static final String[] ADDRESSES =
            new String[] {"ADDRESS_1", "ADDRESS_2"};

    private static final String CURRENT_ADDRESS =
            ADDRESSES[Random.from(new SecureRandom()).nextInt(0, 1)];

    @Override
    public void addNewProduct(NewProductInWarehouseRequest request) {
        WarehouseProduct warehouseProduct = WarehouseProduct.builder()
                .productId(request.getProductId())
                .fragile(request.getFragile())
                .dimension(WarehouseMapper.fromDimensionDto(request.getDimension()))
                .weight(request.getWeight())
                .build();

        warehouseProductRepository.save(warehouseProduct);
    }

    @Override
    public BookedProductsDto checkCart(ShoppingCartDto shoppingCartDto) {


        Set<UUID> productIds = shoppingCartDto.getProducts().keySet();

        List<WarehouseProduct> foundProducts = warehouseProductRepository.findByProductIdIn(productIds);

        checkProducts(foundProducts, productIds);


        List<WarehouseInventory> foundInventory = warehouseInventoryRepository.findAllById(productIds);

        checkInventory(foundInventory);
        checkQuantity(shoppingCartDto, foundInventory);


        boolean hasFragile = isHasFragile(foundProducts);

        double deliveryWeight = getDeliveryWeight(foundProducts);
        double deliveryVolume = getDeliveryVolume(foundProducts);


        return  BookedProductsDto.builder()
                .fragile(hasFragile)
                .deliveryWeight(deliveryWeight)
                .deliveryVolume(deliveryVolume)
                .build();

    }

    private static double getDeliveryVolume(List<WarehouseProduct> foundProducts) {
        return foundProducts.stream()
                .map(WarehouseProduct::getDimension)
                .mapToDouble(dimension -> {
                    double width = dimension.getWidth();
                    double height = dimension.getHeight();
                    double depth = dimension.getDepth();
                    return width * height * height * depth * depth;
                })
                .sum();
    }

    private static double getDeliveryWeight(List<WarehouseProduct> foundProducts) {
        return foundProducts.stream()
                .mapToDouble(WarehouseProduct::getWeight)
                .sum();
    }

    private static boolean isHasFragile(List<WarehouseProduct> foundProducts) {
        return foundProducts.stream()
                .anyMatch(WarehouseProduct::getFragile);
    }

    private static void checkQuantity(ShoppingCartDto shoppingCartDto, List<WarehouseInventory> foundInventory) {
        Map<UUID, Integer> requestedProducts = shoppingCartDto.getProducts();
        List<UUID> missingProducts = new ArrayList<>();

        for (WarehouseInventory inventory : foundInventory) {
            UUID productId = inventory.getProduct().getProductId();
            Integer requestedQuantity = requestedProducts.get(productId);

            Integer availableQuantity = inventory.getQuantity();
            if (availableQuantity < requestedQuantity) {
                missingProducts.add(productId);
            }
        }

        if (!missingProducts.isEmpty()) {
            throw new ProductInShoppingCartLowQuantityInWarehouse("Id продуктов которых не хватает на складе: " + missingProducts);
        }
    }

    private static void checkProducts(List<WarehouseProduct> foundProducts, Set<UUID> productIds) {
        Set<UUID> foundIds = foundProducts.stream()
                .map(WarehouseProduct::getProductId)
                .collect(Collectors.toSet());

        List<UUID> missingIds = productIds.stream()
                .filter(id -> !foundIds.contains(id))
                .toList();

        if (!missingIds.isEmpty()) {
            throw new NotFoundException("Продукты не найдены: " + missingIds);
        }
    }

    private static void checkInventory(List<WarehouseInventory> foundInventory) {
        Set<UUID> foundIds = foundInventory.stream()
                .map(WarehouseInventory::getProductId)
                .collect(Collectors.toSet());

        List<UUID> missingIds = foundIds.stream()
                .filter(id -> !foundIds.contains(id))
                .toList();

        if (!missingIds.isEmpty()) {
            throw new NotFoundException("Продукты для которых в складе 0шт: " + missingIds);
        }
    }

    @Override
    public void addProduct(AddProductToWarehouseRequest request) {

        UUID productId = request.getProductId();
        WarehouseProduct warehouseProduct = getWarehouseProduct(productId);
        WarehouseInventory warehouseInventory = WarehouseInventory.builder()
                .product(warehouseProduct)
                .quantity(request.getQuantity())
                .build();
        warehouseInventoryRepository.save(warehouseInventory);
    }

    @Override
    public AddressDto getAddress() {
        return AddressDto.builder()
                .country(CURRENT_ADDRESS)
                .city(CURRENT_ADDRESS)
                .street(CURRENT_ADDRESS)
                .house(CURRENT_ADDRESS)
                .flat(CURRENT_ADDRESS)
                .build();
    }

    @Override
    public void shippedProducts(ShippedToDeliveryRequest request) {

        OrderBooking orderBooking = getOrderBooking(request.getOrderId());
        orderBooking.setDeliveryId(request.getDeliveryId());
        orderBookingRepository.save(orderBooking);
        // меняем статус на Assembled
        orderClient.assemblyOrder(request.getOrderId());
    }

    @Override
    public void returnProducts(Map<UUID, Integer> products) {

        Set<UUID> productIds = products.keySet();
        List<WarehouseInventory> warehouseInventories = warehouseInventoryRepository.findByProductIdIn(productIds);
        for (WarehouseInventory warehouseInventory : warehouseInventories) {
            UUID productId = warehouseInventory.getProduct().getProductId();
            warehouseInventory.setQuantity(warehouseInventory.getQuantity() + products.get(productId));
        }

        warehouseInventoryRepository.saveAll(warehouseInventories);
    }

    @Override
    public BookedProductsDto assemblyProducts(AssemblyProductsForOrderRequest request) {

        OrderBooking orderBooking = OrderBooking.builder()
                .orderId(request.getOrderId())
                .products(request.getProducts())
                .build();

        orderBookingRepository.save(orderBooking);

        Set<UUID> productIds = request.getProducts().keySet();
        List<WarehouseProduct> foundProducts = warehouseProductRepository.findByProductIdIn(productIds);

        reduceQuantityFromInventory(request.getProducts());


        return BookedProductsDto.builder()
                .deliveryWeight(getDeliveryWeight(foundProducts))
                .deliveryVolume(getDeliveryVolume(foundProducts))
                .fragile(isHasFragile(foundProducts))
                .build();

    }

    private void reduceQuantityFromInventory(Map<UUID, Integer> products) {

        Set<UUID> productIds = products.keySet();
        List<WarehouseInventory> warehouseInventories = warehouseInventoryRepository.findByProductIdIn(productIds);
        for (WarehouseInventory warehouseInventory : warehouseInventories) {
            UUID productId = warehouseInventory.getProduct().getProductId();
            warehouseInventory.setQuantity(warehouseInventory.getQuantity() - products.get(productId));
        }

        warehouseInventoryRepository.saveAll(warehouseInventories);
    }


    private WarehouseProduct getWarehouseProduct(UUID productId) {
        return warehouseProductRepository.findById(productId).orElseThrow(
                () -> new NotFoundException("В складе нет продукта с id: " + productId)
        );
    }

    private OrderBooking getOrderBooking(UUID orderId) {
        return orderBookingRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException("В складе нет бронирования для заказа: " + orderId)
        );
    }
}
