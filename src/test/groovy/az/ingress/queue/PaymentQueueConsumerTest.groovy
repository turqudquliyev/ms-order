package az.ingress.queue

import az.ingress.dao.entity.AddressEntity
import az.ingress.dao.entity.OrderEntity
import az.ingress.model.queue.PaymentRequest
import az.ingress.model.response.AddressResponse
import az.ingress.model.response.OrderResponse
import az.ingress.queue.handler.PaymentQueueConsumer
import az.ingress.service.OrderService
import spock.lang.Specification

import static az.ingress.model.enums.OrderStatus.CREATED
import static az.ingress.model.enums.OrderStatus.PLACED
import static java.math.BigDecimal.TEN

class PaymentQueueConsumerTest extends Specification {
    OrderService orderService
    PaymentQueueConsumer paymentQueueConsumer

    def setup() {
        orderService = Mock()
        paymentQueueConsumer = new PaymentQueueConsumer(orderService)
    }

    def "TestConsume success case"() {
        given:
        def message = '''
                                    {
                                        "orderId": 1
                                    }
                             '''
        def paymentRequest = new PaymentRequest(1L)
        def orderResponse = new OrderResponse(1L, 2L, CREATED, 3L, 4, TEN,
                new AddressResponse("City", "District", "Detail"))
        def addressEntity = AddressEntity.builder()
                .id(1L)
                .city("City")
                .district("District")
                .detail("Detail")
                .build()
        def orderEntity = new OrderEntity(1L, 2L, CREATED, 3L, 4, TEN, addressEntity)
        addressEntity.setOrder(orderEntity)

        when:
        paymentQueueConsumer.consume(message)

        then:
        1 * orderService.getOrderById(paymentRequest.orderId) >> orderResponse
        orderResponse.id == orderEntity.id
        orderResponse.userId == orderEntity.userId
        orderResponse.status == PLACED
        orderResponse.productId == orderEntity.productId
        orderResponse.quantity == orderEntity.quantity
        orderResponse.totalAmount == orderEntity.totalAmount
        orderResponse.address.city == orderEntity.address.city
        orderResponse.address.district == orderEntity.address.district
        orderResponse.address.district == orderEntity.address.district
        1 * orderService.save(orderEntity)
    }

    def "TestConsume JsonProcessingException case"() {
        given:
        def invalidMessage = "invalid message"

        when:
        paymentQueueConsumer.consume(invalidMessage)

        then:
        RuntimeException exception = thrown()
    }
}