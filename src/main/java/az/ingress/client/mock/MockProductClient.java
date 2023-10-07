package az.ingress.client.mock;

import az.ingress.client.ProductClient;
import az.ingress.model.client.ProductRequest;
import az.ingress.model.client.ProductResponse;
import org.springframework.context.annotation.Profile;

@Profile("local")
public class MockProductClient implements ProductClient {
    @Override
    public ProductResponse calculateTotalAmount(ProductRequest request) {
        return null;
    }
}