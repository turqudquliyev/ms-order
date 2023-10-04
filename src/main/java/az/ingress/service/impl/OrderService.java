package az.ingress.service.impl;

import az.ingress.client.ProductClient;
import az.ingress.dao.entity.OrderEntity;
import az.ingress.dao.repository.OrderRepository;
import az.ingress.exception.NotFoundException;
import az.ingress.mapper.OrderMapper;
import az.ingress.model.message.PaymentRequest;
import az.ingress.model.request.CreateOrderRequest;
import az.ingress.model.response.OrderResponse;
import az.ingress.service.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static az.ingress.mapper.OrderMapper.*;
import static az.ingress.mapper.ProductClientMapper.buildProductRequest;
import static az.ingress.model.enums.ExceptionMessage.ORDER_NOT_FOUND;
import static az.ingress.model.enums.OrderStatus.CANCELLED;
import static az.ingress.model.enums.OrderStatus.PLACED;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class OrderService implements IOrderService {
    OrderRepository repository;
    ProductClient productClient;

    @Transactional
    @SneakyThrows
    public Long createOrder(Long userId, CreateOrderRequest createOrderRequest) {
        log.info("OrderService.createOrder called with param => userId:{}, {}", userId, createOrderRequest);
        var productRequest = buildProductRequest(createOrderRequest);
        log.info("ProductClient.calculateTotalAmount called with param {}", productRequest);
        var productResponse = productClient.calculateTotalAmount(productRequest);
        log.info("ProductClient retrieved response => {}", productResponse);
        var address = buildOrderAddressEntity(createOrderRequest);
        var orderDetail = buildOrderDetailEntity(createOrderRequest, productResponse);
        var order = buildOrderEntity(userId, address, orderDetail);
        address.setOrder(order);
        orderDetail.setOrder(order);
        var saved = repository.save(order);
        log.info("saved OrderEntity => {}", saved);
        return saved.getId();
    }

    public List<OrderResponse> getByIdIn(List<Long> orderIds) {
        log.info("OrderService.getByIdIn called with param orderIds:{}", orderIds);
        List<OrderResponse> response = repository
                .findByIdIn(orderIds)
                .stream()
                .map(OrderMapper::buildOrderResponse)
                .toList();
        log.info("OrderResponse => {}", response);
        return response;
    }

    public void cancelOrder(Long id) {
        log.info("OrderService.cancelOrder called with param id:{}", id);
        OrderEntity order = fetchIfExist(id);
        log.info("Order has been retrieved from db => {}", order);
        order.setStatus(CANCELLED);
        OrderEntity saved = repository.save(order);
        log.info("saved Order => {}", saved);
    }

    @RabbitListener(queues = "${rabbitmq.queue.payment}")
    public void placeOrder(PaymentRequest request) {
        log.info("PaymentRequest => {}", request);
        OrderEntity order = fetchIfExist(request.getOrderId());
        log.info("Order entity has been retrieved from db {}", order);
        order.setStatus(PLACED);
        OrderEntity saved = repository.save(order);
        log.info("saved OrderEntity => {}", saved);
    }


    private OrderEntity fetchIfExist(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(ORDER_NOT_FOUND));
    }
}