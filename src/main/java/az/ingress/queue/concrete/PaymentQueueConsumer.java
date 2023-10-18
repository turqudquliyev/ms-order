package az.ingress.queue.concrete;

import az.ingress.model.queue.PaymentRequest;
import az.ingress.queue.abstraction.OrderConsumer;
import az.ingress.service.abstraction.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static az.ingress.mapper.ObjectMapperFactory.OBJECT_MAPPER;
import static az.ingress.model.enums.OrderStatus.PLACED;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class PaymentQueueConsumer implements OrderConsumer {
    OrderService orderService;

    @RabbitListener(queues = "${rabbitmq.payment.queue}")
    public void consume(String message) {
        try {
            var paymentRequest = OBJECT_MAPPER.getInstance().readValue(message, PaymentRequest.class);
            orderService.updateOrderStatus(paymentRequest.getOrderId(), PLACED);
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException: ", e);
        }
    }
}