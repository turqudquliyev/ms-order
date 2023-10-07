package az.ingress.mapper;

import az.ingress.dao.entity.AddressEntity;
import az.ingress.dao.entity.OrderEntity;
import az.ingress.model.client.ProductResponse;
import az.ingress.model.request.CreateOrderRequest;
import az.ingress.model.response.OrderResponse;

import static az.ingress.mapper.AddressMapper.ADDRESS_MAPPER;
import static az.ingress.model.enums.OrderStatus.CREATED;

public enum OrderMapper {
    ORDER_MAPPER;

    public OrderEntity buildOrderEntity(Long userId,
                                        CreateOrderRequest createOrderRequest,
                                        AddressEntity address,
                                        ProductResponse productResponse) {
        return OrderEntity.builder()
                .userId(userId)
                .status(CREATED)
                .productId(createOrderRequest.getProductId())
                .quantity(createOrderRequest.getQuantity())
                .totalAmount(productResponse.getTotalAmount())
                .address(address)
                .build();
    }

    public OrderResponse buildOrderResponse(OrderEntity order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .status(order.getStatus())
                .productId(order.getProductId())
                .quantity(order.getQuantity())
                .totalAmount(order.getTotalAmount())
                .address(ADDRESS_MAPPER.buildAddressResponse(order.getAddress()))
                .build();
    }

    public OrderEntity mapOrderResponseToOrderEntity(OrderResponse orderResponse) {
        return OrderEntity.builder()
                .id(orderResponse.getId())
                .userId(orderResponse.getUserId())
                .status(orderResponse.getStatus())
                .productId(orderResponse.getProductId())
                .quantity(orderResponse.getQuantity())
                .totalAmount(orderResponse.getTotalAmount())
                .address(ADDRESS_MAPPER.mapAddressResponseToAddressEntity(orderResponse.getAddress()))
                .build();
    }
}