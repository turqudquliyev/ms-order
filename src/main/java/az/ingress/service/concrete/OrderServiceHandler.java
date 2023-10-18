package az.ingress.service.concrete;

import az.ingress.client.ProductClient;
import az.ingress.dao.entity.OrderEntity;
import az.ingress.dao.repository.OrderRepository;
import az.ingress.exception.NotFoundException;
import az.ingress.model.client.ProductResponse;
import az.ingress.model.enums.OrderStatus;
import az.ingress.model.request.OrderRequest;
import az.ingress.model.response.OrderResponse;
import az.ingress.service.abstraction.OrderService;
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

    public OrderResponse createOrder(Long userId, OrderRequest orderRequest) {
        var productRequest = PRODUCT_MAPPER.buildProductRequest(orderRequest);
        var productResponse = productClient.calculateTotalAmount(productRequest);
        var order = getOrderEntity(userId, orderRequest, productResponse);
        var savedOrder = orderRepository.save(order);
        return ORDER_MAPPER.mapEntityToResponse(savedOrder);
    }

    public void updateOrderStatus(Long id, OrderStatus status) {
        OrderEntity order = fetchIfExist(id);
        order.setStatus(status);
        orderRepository.save(order);
    }

    public List<OrderResponse> getOrderByIdIn(List<Long> orderIds) {
        return orderRepository
                .findByIdIn(orderIds)
                .stream()
                .map(ORDER_MAPPER::mapEntityToResponse)
                .toList();
    }

    public void cancelOrder(Long id) {
        var order = fetchIfExist(id);
        order.setStatus(CANCELLED);
        orderRepository.save(order);
    }

    public OrderResponse getOrderById(Long id) {
        var order = fetchIfExist(id);
        return ORDER_MAPPER.mapEntityToResponse(order);
    }

    public OrderEntity save(OrderEntity order) {
        return orderRepository.save(order);
    }


    private OrderEntity fetchIfExist(Long id) {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(ORDER_NOT_FOUND));
    }

    private OrderEntity getOrderEntity(Long userId,
                                       OrderRequest orderRequest,
                                       ProductResponse productResponse) {
        var address = ADDRESS_MAPPER.mapRequestToEntity(orderRequest.getAddress());
        var order = ORDER_MAPPER.buildOrderEntity(userId, orderRequest, address, productResponse.getTotalAmount());
        address.setOrder(order);
        return order;
    }
}