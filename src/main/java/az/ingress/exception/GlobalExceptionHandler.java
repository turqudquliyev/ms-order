package az.ingress.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static az.ingress.model.enums.ExceptionMessage.UNEXPECTED_EXCEPTION;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ExceptionResponse handle(NotFoundException ex) {
        log.error("NotFoundException: {}", Arrays.toString(ex.getStackTrace()));
        return new ExceptionResponse(ex.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public Map<String, String> handle(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException: {}", Arrays.toString(ex.getStackTrace()));
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            log.error("{} : {}", fieldError.getField(), fieldError.getDefaultMessage());
        }
        return errors;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(METHOD_NOT_ALLOWED)
    public ExceptionResponse handle(HttpRequestMethodNotSupportedException ex) {
        log.error("MethodNotAllowedException: {}", Arrays.toString(ex.getStackTrace()));
        return new ExceptionResponse(ex.getMessage());
    }

    @ExceptionHandler(value = CustomFeignException.class)
    public ResponseEntity<ExceptionResponse> handle(CustomFeignException ex) {
        log.error("CustomFeignException: {}", Arrays.toString(ex.getStackTrace()));
        return ResponseEntity.status(ex.getStatusCode()).body(new ExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ExceptionResponse handle(UnexpectedException ex) {
        log.error("UnexpectedException: {}", Arrays.toString(ex.getStackTrace()));
        return new ExceptionResponse(UNEXPECTED_EXCEPTION.toString());
    }
}