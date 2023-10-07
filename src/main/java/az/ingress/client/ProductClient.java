package az.ingress.client;

import az.ingress.client.decoder.CustomErrorDecoder;
import az.ingress.model.client.ProductRequest;
import az.ingress.model.client.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Profile("!local")
@FeignClient(name = "product-service",
        url = "${client.urls.product-service}",
        configuration = CustomErrorDecoder.class
)
public interface ProductClient {
    @PostMapping("/calculate")
    ProductResponse calculateTotalAmount(@RequestBody ProductRequest request);
}