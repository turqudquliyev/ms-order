package az.ingress.queue.handler;

import az.ingress.model.queue.PaymentRequest;
import az.ingress.queue.OrderConsumer;
import az.ingress.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static az.ingress.mapper.ObjectMapperFactory.OBJECT_MAPPER;
import static az.ingress.mapper.OrderMapper.ORDER_MAPPER;
import static az.ingress.model.enums.OrderStatus.PLACED;
import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class PaymentQueueConsumer implements OrderConsumer {
    OrderService orderService;

    @RabbitListener(queues = "${rabbitmq.queue.payment}")
    public void consume(String message) {
        try {
            var paymentRequest = OBJECT_MAPPER.getInstance().readValue(message, PaymentRequest.class);
            var orderResponse = orderService.getOrderById(paymentRequest.getOrderId());
            orderResponse.setStatus(PLACED);
            var order = ORDER_MAPPER.mapResponseToEntity(orderResponse);
            orderService.save(order);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}