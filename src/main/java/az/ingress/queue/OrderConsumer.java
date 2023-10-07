//package az.ingress.queue;
//
//import az.ingress.dao.entity.OrderEntity;
//import az.ingress.model.queue.PaymentRequest;
//import az.ingress.model.response.OrderResponse;
//import az.ingress.service.OrderService;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Service;
//
//import static az.ingress.mapper.OrderMapper.ORDER_MAPPER;
//import static az.ingress.model.enums.OrderStatus.PLACED;
//import static lombok.AccessLevel.PRIVATE;
//
//@Service
//@RequiredArgsConstructor
//@FieldDefaults(makeFinal = true, level = PRIVATE)
//public class OrderConsumer {
//    OrderService orderService;
//
//    @RabbitListener(queues = "${rabbitmq.queue.payment}")
//    public void placeOrder(PaymentRequest request) {
//        OrderResponse orderResponse = orderService.getOrderById(request.getOrderId());
//        orderResponse.setStatus(PLACED);
//        OrderEntity order = ORDER_MAPPER.mapOrderResponseToOrderEntity(orderResponse);
//        orderService.save(order);
//    }
//}
