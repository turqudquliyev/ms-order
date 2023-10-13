package az.ingress.mapper;

import az.ingress.dao.entity.OrderEntity;
import az.ingress.model.response.OrderResponse;

import static az.ingress.mapper.AddressMapper.ADDRESS_MAPPER;

public enum OrderMapper {
    ORDER_MAPPER;

    public OrderResponse mapEntityToResponse(OrderEntity order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .status(order.getStatus())
                .productId(order.getProductId())
                .quantity(order.getQuantity())
                .totalAmount(order.getTotalAmount())
                .address(ADDRESS_MAPPER.mapEntityToResponse(order.getAddress()))
                .build();
    }

    public OrderEntity mapResponseToEntity(OrderResponse orderResponse) {
        return OrderEntity.builder()
                .id(orderResponse.getId())
                .userId(orderResponse.getUserId())
                .status(orderResponse.getStatus())
                .productId(orderResponse.getProductId())
                .quantity(orderResponse.getQuantity())
                .totalAmount(orderResponse.getTotalAmount())
                .address(ADDRESS_MAPPER.mapResponseToEntity(orderResponse.getAddress()))
                .build();
    }
}