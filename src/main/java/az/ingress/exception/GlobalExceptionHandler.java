package az.ingress.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static az.ingress.model.enums.ExceptionMessage.UNEXPECTED_EXCEPTION;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseStatus(NOT_FOUND)
    public ExceptionResponse handle(NotFoundException ex) {
        return new ExceptionResponse(ex.getMessage());
    }

    @ExceptionHandler(value = {UnexpectedException.class})
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ExceptionResponse handle(Exception ex) {
        log.error("An unexpected error occurred: {}", ex.getMessage());
        return new ExceptionResponse(UNEXPECTED_EXCEPTION.toString());
    }
}