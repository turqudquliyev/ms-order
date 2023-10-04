package az.ingress.mapper;

import az.ingress.dao.entity.OrderAddressEntity;
import az.ingress.dao.entity.OrderDetailEntity;
import az.ingress.dao.entity.OrderEntity;
import az.ingress.model.client.ProductResponse;
import az.ingress.model.request.CreateOrderRequest;
import az.ingress.model.response.AddressResponse;
import az.ingress.model.response.OrderDetailResponse;
import az.ingress.model.response.OrderResponse;

import static az.ingress.model.enums.OrderStatus.CREATED;

public class OrderMapper {
    public static OrderEntity buildOrderEntity(Long userId,
                                               OrderAddressEntity address,
                                               OrderDetailEntity orderDetail) {
        return OrderEntity.builder()
                .userId(userId)
                .status(CREATED)
                .orderDetail(orderDetail)
                .address(address)
                .build();
    }

    public static OrderAddressEntity buildOrderAddressEntity(CreateOrderRequest createOrderRequest) {
        return OrderAddressEntity.builder()
                .city(createOrderRequest.getAddress().getCity())
                .district(createOrderRequest.getAddress().getDistrict())
                .addressDetail(createOrderRequest.getAddress().getAddressDetail())
                .build();
    }

    public static OrderDetailEntity buildOrderDetailEntity(CreateOrderRequest createOrderRequest, ProductResponse productResponse) {
        return OrderDetailEntity.builder()
                .productId(createOrderRequest.getItem().getProductId())
                .quantity(createOrderRequest.getItem().getQuantity())
                .totalAmount(productResponse.getTotalAmount())
                .build();
    }

    public static OrderDetailResponse buildOrderDetailResponse(OrderDetailEntity orderDetail) {
        return OrderDetailResponse.builder()
                .productId(orderDetail.getProductId())
                .quantity(orderDetail.getQuantity())
                .totalAmount(orderDetail.getTotalAmount())
                .build();
    }

    public static AddressResponse buildAddressResponse(OrderAddressEntity orderAddress) {
        return AddressResponse.builder()
                .city(orderAddress.getCity())
                .district(orderAddress.getDistrict())
                .city(orderAddress.getCity())
                .build();
    }

    public static OrderResponse buildOrderResponse(OrderEntity order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .status(order.getStatus())
                .detail(buildOrderDetailResponse(order.getOrderDetail()))
                .address(buildAddressResponse(order.getAddress()))
                .build();
    }
}