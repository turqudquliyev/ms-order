package az.ingress.client.decoder;

import az.ingress.exception.CustomFeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import static az.ingress.model.enums.ExceptionMessage.CLIENT_EXCEPTION;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        log.error("response {}, methodKey {}", response, methodKey);
        throw new CustomFeignException(CLIENT_EXCEPTION, response.status());
    }
}