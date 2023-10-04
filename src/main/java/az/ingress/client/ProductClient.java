package az.ingress.client;

import az.ingress.model.client.ProductRequest;
import az.ingress.model.client.ProductResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(name = "product-service", url = "${client.urls.product-service}")
public interface ProductClient {
    @PostMapping("/products/calculate")
    ProductResponse calculateTotalAmount(@RequestBody ProductRequest request);
}