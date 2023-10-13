package az.ingress.queue;

public interface OrderConsumer {
    void consume(String message);
}