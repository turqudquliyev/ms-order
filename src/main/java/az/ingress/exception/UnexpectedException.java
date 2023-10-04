package az.ingress.exception;


import az.ingress.model.enums.ExceptionMessage;

public class UnexpectedException extends Exception {
    public UnexpectedException(ExceptionMessage message) {
        super(message.toString());
    }
}