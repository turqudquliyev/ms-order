package az.ingress.mapper;

import az.ingress.model.client.ProductRequest;
import az.ingress.model.request.OrderRequest;

public enum ProductMapper {
    PRODUCT_MAPPER;

    public ProductRequest buildProductRequest(OrderRequest orderRequest) {
        return ProductRequest.builder()
                .productId(orderRequest.getProductId())
                .quantity(orderRequest.getQuantity())
                .build();
    }
}