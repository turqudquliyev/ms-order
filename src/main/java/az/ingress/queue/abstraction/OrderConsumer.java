package az.ingress.queue.abstraction;

public interface OrderConsumer {
    void consume(String message);
}