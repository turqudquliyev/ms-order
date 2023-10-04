package az.ingress.client.decoder;

import az.ingress.exception.NotFoundException;
import az.ingress.exception.UnexpectedException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static az.ingress.model.enums.ExceptionMessage.PRODUCT_NOT_IN_STOCK;
import static az.ingress.model.enums.ExceptionMessage.UNEXPECTED_EXCEPTION;

@Slf4j
@Component
public class FeignClientDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        log.info("response {}, methodKey {}", response, methodKey);
        return switch (response.status()) {
            case 404 -> new NotFoundException(PRODUCT_NOT_IN_STOCK);
            default -> new UnexpectedException(UNEXPECTED_EXCEPTION);
        };
    }
}