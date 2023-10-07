package az.ingress.mapper;

import az.ingress.model.client.ProductRequest;
import az.ingress.model.request.CreateOrderRequest;

public enum ProductMapper {
    PRODUCT_MAPPER;

    public ProductRequest buildProductRequest(CreateOrderRequest createOrderRequest) {
        return ProductRequest.builder()
                .productId(createOrderRequest.getProductId())
                .quantity(createOrderRequest.getQuantity())
                .build();
    }
}