package az.ingress.exception;


import az.ingress.model.enums.ExceptionMessage;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class CustomFeignException extends RuntimeException {
    Integer statusCode;

    public CustomFeignException(ExceptionMessage message, Integer statusCode) {
        super(message.toString());
        this.statusCode = statusCode;
    }
}