package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.clients.DeliveryClient;
import ru.practicum.clients.PaymentClient;
import ru.practicum.clients.WarehouseClient;
import ru.practicum.dto.delivery.DeliveryDto;
import ru.practicum.dto.order.CreateNewOrderRequest;
import ru.practicum.dto.order.OrderDto;
import ru.practicum.dto.order.ProductReturnRequest;
import ru.practicum.dto.order.enums.OrderState;
import ru.practicum.dto.payment.PaymentDto;
import ru.practicum.dto.warehouse.AssemblyProductsForOrderRequest;
import ru.practicum.dto.warehouse.BookedProductsDto;
import ru.practicum.dto.warehouse.ShippedToDeliveryRequest;
import ru.practicum.exception.NoOrderFoundException;
import ru.practicum.model.Order;
import ru.practicum.repository.OrderRepository;
import ru.practicum.utils.Mapper;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final WarehouseClient warehouseClient;
    private final DeliveryClient deliveryClient;
    private final PaymentClient paymentClient;

    @Override
    public List<OrderDto> getOrders(String username) {

        List<Order> orders = orderRepository.findByUsername(username);
        if (orders.isEmpty()) {
            throw new NoOrderFoundException("No orders found for username: " + username);
        }

        return orders.stream().
                map(Mapper::toOrderDto)
                .toList();
    }

    @Override
    public OrderDto createOrder(String username, CreateNewOrderRequest request) {

        Order order = Order.builder()
                .username(username)
                .shoppingCartId(request.getShoppingCart().getShoppingCartId())
                .orderAddress(Mapper.toAddress(request.getDetailAddress()))
                .products(request.getShoppingCart().getProducts())
                .state(OrderState.NEW)
                .build();

        // Сохранение стоимости продуктов
        OrderDto orderDto =  Mapper.toOrderDto(order);
        Double productPrice =  paymentClient.productCost(orderDto);
        order.setProductPrice(productPrice);

        //Предварительный подсчет заказа
        BookedProductsDto bookedProductsDto = warehouseClient.checkCart(request.getShoppingCart());
        order.setDeliveryVolume(bookedProductsDto.getDeliveryVolume());
        order.setFragile(bookedProductsDto.getFragile());
        order.setDeliveryWeight(bookedProductsDto.getDeliveryWeight());

        return Mapper.toOrderDto(orderRepository.save(order));
    }

    @Override
    public OrderDto returnOrder(ProductReturnRequest request) {
        return null;
    }

    @Override
    public OrderDto payOrder(UUID orderId) {

        Order order = getOrderById(orderId);
        order.setState(OrderState.PAID);

        return Mapper.toOrderDto(orderRepository.save(order));


    }

    @Override
    public OrderDto payOrderFailed(UUID orderId) {
        Order order = getOrderById(orderId);
        order.setState(OrderState.PAYMENT_FAILED);


        return Mapper.toOrderDto(orderRepository.save(order));


    }

    @Override
    public OrderDto deliveryOrder(UUID orderId) {

        Order order = getOrderById(orderId);
        // передаем в доставку
        ShippedToDeliveryRequest request = ShippedToDeliveryRequest.builder()
                .deliveryId(order.getDeliveryId())
                .orderId(orderId)
                .build();
        warehouseClient.shippedProducts(request);
    }

    @Override
    public OrderDto deliveryOrderFailed(UUID orderId) {
        return null;
    }

    @Override
    public OrderDto completedOrder(UUID orderId) {
        return null;
    }

    @Override
    public OrderDto calculateTotal(UUID orderId) {
        Order order = getOrderById(orderId);
        OrderDto orderDto = Mapper.toOrderDto(order);
        //Создание платежа
        PaymentDto paymentDto = paymentClient.createPayment(orderDto);
        Double totalCost = paymentClient.totalCost(orderDto);

        order.setPaymentId(paymentDto.getPaymentId());
        order.setTotalPrice(totalCost);
        order.setState(OrderState.ON_PAYMENT);
        return Mapper.toOrderDto(orderRepository.save(order));
    }

    @Override
    public OrderDto calculateDelivery(UUID orderId) {
        Order order = getOrderById(orderId);

        //Резервирование товара
        AssemblyProductsForOrderRequest assemblyRequest = AssemblyProductsForOrderRequest.builder()
                .products(order.getProducts())
                .orderId(orderId)
                .build();
        warehouseClient.assemblyProducts(assemblyRequest);


        Double deliveryPrice = 0.0;
        if (order.getDeliveryPrice() != null) {   //если уже считали заказ
            return Mapper.toOrderDto(order);
        } else {

            //создаем предварительно доставку
            DeliveryDto deliveryDto= DeliveryDto.builder()
                    .fromAddress(Mapper.toAddressDto(order.getOrderAddress()))
                    .toAddress(warehouseClient.getAddress())
                    .orderId(orderId)
                    .build();
            UUID deliveryId = deliveryClient.addDelivery(deliveryDto).getDeliveryId();
            order.setDeliveryId(deliveryId);  // добавляем в заказ id доставки
            deliveryPrice = deliveryClient.deliveryCost(Mapper.toOrderDto(order));
        }

        order.setDeliveryPrice(deliveryPrice); // добавляем в заказ цену доставки
        return Mapper.toOrderDto(orderRepository.save(order));
    }

    @Override
    public OrderDto assemblyOrder(UUID orderId) {
        Order order = getOrderById(orderId);
        order.setState(OrderState.ASSEMBLED);

        return Mapper.toOrderDto(orderRepository.save(order));
    }

    @Override
    public OrderDto assemblyOrderFailed(UUID orderId) {
        Order order = getOrderById(orderId);
        order.setState(OrderState.ASSEMBLY_FAILED);

        return Mapper.toOrderDto(orderRepository.save(order));
    }

    private Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new NoOrderFoundException("Order not found for id: " + orderId)
        );
    }
}
