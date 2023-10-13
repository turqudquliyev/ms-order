package az.ingress.service

import az.ingress.client.ProductClient
import az.ingress.client.mock.MockProductClient
import az.ingress.dao.entity.OrderEntity
import az.ingress.dao.repository.OrderRepository
import az.ingress.exception.NotFoundException
import az.ingress.model.enums.OrderStatus
import az.ingress.service.handler.OrderServiceHandler
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import io.github.benas.randombeans.randomizers.misc.EnumRandomizer
import spock.lang.Specification

import static az.ingress.model.enums.OrderStatus.CANCELLED

class OrderServiceTest extends Specification {
    EnhancedRandom random = EnhancedRandomBuilder
            .aNewEnhancedRandomBuilder()
            .randomize(OrderStatus.class, new EnumRandomizer<>(OrderStatus.class))
            .build()
    ProductClient productClient
    OrderRepository orderRepository
    OrderService orderService

    def setup() {
        orderRepository = Mock()
        productClient = new MockProductClient()
        orderService = new OrderServiceHandler(orderRepository, productClient)
    }

    def "TestGetOrderById success case"() {
        given:
        def id = random.nextLong()
        def orderEntity = random.nextObject(OrderEntity)

        when:
        def actual = orderService.getOrderById(id)

        then:
        1 * orderRepository.findById(id) >> Optional.of(orderEntity)
        actual.id == orderEntity.id
        actual.userId == orderEntity.userId
        actual.status == orderEntity.status
        actual.productId == orderEntity.productId
        actual.quantity == orderEntity.quantity
        actual.totalAmount == orderEntity.totalAmount
        actual.address.city == orderEntity.address.city
        actual.address.district == orderEntity.address.district
        actual.address.detail == orderEntity.address.detail
    }

    def "TestGetOrderById OrderNotFound case"() {
        given:
        def id = random.nextLong()

        when:
        orderService.getOrderById(id)

        then:
        1 * orderRepository.findById(id) >> Optional.empty()
        NotFoundException exception = thrown()
        exception.message == "ORDER_NOT_FOUND"
    }

    def "TestSave success case"() {
        given:
        def orderEntity = random.nextObject(OrderEntity)

        when:
        def actual = orderService.save(orderEntity)

        then:
        1 * orderRepository.save(orderEntity) >> orderEntity
        actual.id == orderEntity.id
        actual.userId == orderEntity.userId
        actual.status == orderEntity.status
        actual.productId == orderEntity.productId
        actual.quantity == orderEntity.quantity
        actual.totalAmount == orderEntity.totalAmount
        actual.address.id == orderEntity.address.id
        actual.address.city == orderEntity.address.city
        actual.address.district == orderEntity.address.district
        actual.address.detail == orderEntity.address.detail
    }

    def "TestCancelOrder success case"() {
        given:
        def id = random.nextLong()
        def orderEntity = random.nextObject(OrderEntity)

        when:
        orderService.cancelOrder(id)

        then:
        1 * orderRepository.findById(id) >> Optional.of(orderEntity)
        1 * orderRepository.save(orderEntity)
        orderEntity.status == CANCELLED
    }

    def "TestCancelOrder OrderNotFound case"() {
        given:
        def id = random.nextLong()

        when:
        orderService.cancelOrder(id)

        then:
        1 * orderRepository.findById(id) >> Optional.empty()
        NotFoundException exception = thrown()
        exception.message == "ORDER_NOT_FOUND"
    }

    def "TestGetOrderByIdIn success case"() {
        given:
        def ids = random.nextObject(List<Long>)
        def orderEntity = random.nextObject(OrderEntity)
        def orderEntities = [orderEntity]

        when:
        def actual = orderService.getOrderByIdIn(ids)

        then:
        1 * orderRepository.findByIdIn(ids) >> orderEntities
        actual[0].id == orderEntities[0].id
        actual[0].userId == orderEntities[0].userId
        actual[0].status == orderEntities[0].status
        actual[0].productId == orderEntities[0].productId
        actual[0].quantity == orderEntities[0].quantity
        actual[0].totalAmount == orderEntities[0].totalAmount
        actual[0].address.city == orderEntities[0].address.city
        actual[0].address.district == orderEntities[0].address.district
        actual[0].address.detail == orderEntities[0].address.detail
    }
}