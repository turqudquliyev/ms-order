package az.ingress.service;

import az.ingress.dao.entity.OrderEntity;
import az.ingress.model.request.OrderRequest;
import az.ingress.model.response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(Long userId, OrderRequest orderRequest);

    List<OrderResponse> getOrderByIdIn(List<Long> orderIds);

    void cancelOrder(Long id);

    OrderResponse getOrderById(Long id);

    OrderEntity save(OrderEntity order);
}