package az.ingress.mapper


import az.ingress.dao.entity.OrderEntity
import az.ingress.model.enums.OrderStatus
import az.ingress.model.response.OrderResponse
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import io.github.benas.randombeans.randomizers.misc.EnumRandomizer
import spock.lang.Specification

import static az.ingress.mapper.OrderMapper.ORDER_MAPPER

class OrderMapperTest extends Specification {
    EnhancedRandom random = EnhancedRandomBuilder
            .aNewEnhancedRandomBuilder()
            .randomize(OrderStatus.class, new EnumRandomizer<>(OrderStatus.class))
            .build()

    def "TestMapEntityToResponse"() {
        given:
        def orderEntity = random.nextObject(OrderEntity)

        when:
        def orderResponse = ORDER_MAPPER.mapEntityToResponse(orderEntity)

        then:
        orderEntity.id == orderResponse.id
        orderEntity.userId == orderResponse.userId
        orderEntity.status == orderResponse.status
        orderEntity.productId == orderResponse.productId
        orderEntity.quantity == orderResponse.quantity
        orderEntity.totalAmount == orderResponse.totalAmount
        orderEntity.address.city == orderResponse.address.city
        orderEntity.address.district == orderResponse.address.district
        orderEntity.address.detail == orderResponse.address.detail
    }

    def "TestMapResponseToEntity"() {
        given:
        def orderResponse = random.nextObject(OrderResponse)

        when:
        def orderEntity = ORDER_MAPPER.mapResponseToEntity(orderResponse)

        then:
        orderResponse.id == orderEntity.id
        orderResponse.userId == orderEntity.userId
        orderResponse.status == orderEntity.status
        orderResponse.productId == orderEntity.productId
        orderResponse.quantity == orderEntity.quantity
        orderResponse.totalAmount == orderEntity.totalAmount
        orderResponse.address.city == orderEntity.address.city
        orderResponse.address.district == orderEntity.address.district
        orderResponse.address.detail == orderEntity.address.detail
    }
}