package az.ingress.service;

import az.ingress.model.request.CreateOrderRequest;
import az.ingress.model.response.OrderResponse;

import java.util.List;

public interface IOrderService {
    Long createOrder(Long userId, CreateOrderRequest createOrderRequest);

    List<OrderResponse> getByIdIn(List<Long> orderIds);

    void cancelOrder(Long id);
}