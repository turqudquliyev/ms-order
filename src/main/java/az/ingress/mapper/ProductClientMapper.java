package az.ingress.mapper;

import az.ingress.model.client.ProductRequest;
import az.ingress.model.request.CreateOrderRequest;

public class ProductClientMapper {
    public static ProductRequest buildProductRequest(CreateOrderRequest request) {
        return ProductRequest.builder()
                .productId(request.getItem().getProductId())
                .quantity(request.getItem().getQuantity())
                .build();
    }
}