package az.ingress.model.enums;

public enum ExceptionMessage {
    PRODUCT_NOT_IN_STOCK,
    ORDER_NOT_FOUND,
    UNEXPECTED_EXCEPTION;

    public static String toString(ExceptionMessage exceptionMessage) {
        return exceptionMessage.toString();
    }
}