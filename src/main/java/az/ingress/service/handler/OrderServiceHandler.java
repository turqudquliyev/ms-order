package az.ingress.service.handler;

import az.ingress.aop.annotation.Loggable;
import az.ingress.client.ProductClient;
import az.ingress.dao.entity.OrderEntity;
import az.ingress.dao.repository.OrderRepository;
import az.ingress.exception.NotFoundException;
import az.ingress.model.client.ProductResponse;
import az.ingress.model.request.CreateOrderRequest;
import az.ingress.model.response.OrderResponse;
import az.ingress.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

import static az.ingress.mapper.AddressMapper.ADDRESS_MAPPER;
import static az.ingress.mapper.OrderMapper.ORDER_MAPPER;
import static az.ingress.mapper.ProductMapper.PRODUCT_MAPPER;
import static az.ingress.model.enums.ExceptionMessage.ORDER_NOT_FOUND;
import static az.ingress.model.enums.OrderStatus.CANCELLED;
import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class OrderServiceHandler implements OrderService {
    OrderRepository orderRepository;
    ProductClient productClient;

    @Loggable
    public OrderResponse createOrder(Long userId, CreateOrderRequest createOrderRequest) {
        var productRequest = PRODUCT_MAPPER.buildProductRequest(createOrderRequest);
        var productResponse = productClient.calculateTotalAmount(productRequest);
        var order = getOrderEntity(userId, createOrderRequest, productResponse);
        var savedOrder = orderRepository.save(order);
        return ORDER_MAPPER.buildOrderResponse(savedOrder);
    }

    @Loggable
    public List<OrderResponse> getOrderByIdIn(List<Long> orderIds) {
        return orderRepository
                .findByIdIn(orderIds)
                .stream()
                .map(ORDER_MAPPER::buildOrderResponse)
                .toList();
    }

    @Loggable
    public void cancelOrder(Long id) {
        OrderEntity order = fetchIfExist(id);
        order.setStatus(CANCELLED);
        orderRepository.save(order);
    }

    @Loggable
    public OrderResponse getOrderById(Long id) {
        OrderEntity order = fetchIfExist(id);
        return ORDER_MAPPER.buildOrderResponse(order);
    }

    @Loggable
    public OrderEntity save(OrderEntity order) {
        return orderRepository.save(order);
    }

    @Loggable
    private static OrderEntity getOrderEntity(Long userId,
                                              CreateOrderRequest createOrderRequest,
                                              ProductResponse productResponse) {
        var address = ADDRESS_MAPPER.buildAddressEntity(createOrderRequest.getAddress());
        var order = ORDER_MAPPER.buildOrderEntity(userId, createOrderRequest, address, productResponse);
        address.setOrder(order);
        return order;
    }

    @Loggable
    private OrderEntity fetchIfExist(Long id) {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(ORDER_NOT_FOUND));
    }
}