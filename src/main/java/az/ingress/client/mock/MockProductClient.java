package az.ingress.client.mock;

import az.ingress.client.ProductClient;
import az.ingress.model.client.ProductRequest;
import az.ingress.model.client.ProductResponse;
import org.springframework.stereotype.Component;

import static java.math.BigDecimal.TEN;

@Component
public class MockProductClient implements ProductClient {
    @Override
    public ProductResponse calculateTotalAmount(ProductRequest request) {
        return ProductResponse.builder()
                .totalAmount(TEN)
                .build();
    }
}