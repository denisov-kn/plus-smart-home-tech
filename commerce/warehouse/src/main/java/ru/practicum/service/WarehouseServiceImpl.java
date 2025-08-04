package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.practicum.model.WarehouseInventory;
import ru.practicum.repository.WarehouseInventoryRepository;
import ru.practicum.repository.WarehouseProductRepository;
import ru.practicum.dto.shoppingCart.ShoppingCartDto;
import ru.practicum.dto.warehouse.AddProductToWarehouseRequest;
import ru.practicum.dto.warehouse.AddressDto;
import ru.practicum.dto.warehouse.BookedProductsDto;
import ru.practicum.dto.warehouse.NewProductInWarehouseRequest;
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

    private static final String[] ADDRESSES =
            new String[] {"ADDRESS_1", "ADDRESS_2"};

    private static final String CURRENT_ADDRESS =
            ADDRESSES[Random.from(new SecureRandom()).nextInt(0, 1)];

    @Override
    public void addNewProduct(NewProductInWarehouseRequest request) {
        WarehouseProduct warehouseProduct = WarehouseProduct.builder()
                .productId(UUID.fromString(request.getProductId()))
                .fragile(request.getFragile())
                .dimension(WarehouseMapper.fromDimensionDto(request.getDimension()))
                .weight(request.getWeight())
                .build();

        warehouseProductRepository.save(warehouseProduct);
    }

    @Override
    public BookedProductsDto checkCart(ShoppingCartDto shoppingCartDto) {


        Set<UUID> productIds = getUuids(shoppingCartDto);

        List<WarehouseProduct> foundProducts = warehouseProductRepository.findByProductIdIn(productIds);

        checkProducts(foundProducts, productIds);


        List<WarehouseInventory> foundInventory = warehouseInventoryRepository.findAllById(productIds);

        checkInventory(foundInventory);
        checkQuantity(shoppingCartDto, foundInventory);


        boolean hasFragile = foundProducts.stream()
                .anyMatch(WarehouseProduct::getFragile);

        double deliveryWeight = foundProducts.stream()
                .mapToDouble(WarehouseProduct::getWeight)
                .sum();
        double deliveryVolume = foundProducts.stream()
                .map(WarehouseProduct::getDimension)
                        .mapToDouble(dimension -> {
                            double width = dimension.getWidth();
                            double height = dimension.getHeight();
                            double depth = dimension.getDepth();
                            return width * height * height * depth * depth;
                                })
                .sum();


        return  BookedProductsDto.builder()
                .fragile(hasFragile)
                .deliveryWeight(deliveryWeight)
                .deliveryVolume(deliveryVolume)
                .build();



    }

    private static void checkQuantity(ShoppingCartDto shoppingCartDto, List<WarehouseInventory> foundInventory) {
        Map<String, Integer> requestedProducts = shoppingCartDto.getProducts();
        List<String> missingProducts = new ArrayList<>();

        for (WarehouseInventory inventory : foundInventory) {
            String productId = inventory.getProduct().getProductId().toString();
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

    private void checkProduct(ShoppingCartDto shoppingCartDto) {

    }

    private static Set<UUID> getUuids(ShoppingCartDto shoppingCartDto) {
        Set<UUID> productIds = shoppingCartDto.getProducts().keySet().stream()
                .map(UUID::fromString)
                .collect(Collectors.toSet());
        return productIds;
    }

    @Override
    public void addProduct(AddProductToWarehouseRequest request) {

        UUID productId = UUID.fromString(request.getProductId());
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

    private WarehouseProduct getWarehouseProduct(UUID productId) {
        return warehouseProductRepository.findById(productId).orElseThrow(
                () -> new NotFoundException("В склде нет продукта с id: " + productId)
        );
    }
}
