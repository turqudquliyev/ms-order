package az.ingress.exception;


import az.ingress.model.enums.ExceptionMessage;

public class UnexpectedException extends RuntimeException {
    public UnexpectedException(ExceptionMessage message) {
        super(message.toString());
    }
}