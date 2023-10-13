package az.ingress.mapper

import az.ingress.dao.entity.AddressEntity
import az.ingress.model.enums.OrderStatus
import az.ingress.model.request.AddressRequest
import az.ingress.model.response.AddressResponse
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import io.github.benas.randombeans.randomizers.misc.EnumRandomizer
import spock.lang.Specification

import static az.ingress.mapper.AddressMapper.ADDRESS_MAPPER

class AddressMapperTest extends Specification {
    EnhancedRandom random = EnhancedRandomBuilder
            .aNewEnhancedRandomBuilder()
            .randomize(OrderStatus.class, new EnumRandomizer<>(OrderStatus.class))
            .build()

    def "TestMapRequestToEntity"() {
        given:
        def addressRequest = random.nextObject(AddressRequest)

        when:
        def addressEntity = ADDRESS_MAPPER.mapRequestToEntity(addressRequest)

        then:
        addressRequest.city == addressEntity.city
        addressRequest.district == addressEntity.district
        addressRequest.detail == addressEntity.detail
    }

    def "TestMapEntityToResponse"() {
        given:
        def addressEntity = random.nextObject(AddressEntity)

        when:
        def addressResponse = ADDRESS_MAPPER.mapEntityToResponse(addressEntity)

        then:
        addressEntity.city == addressResponse.city
        addressEntity.district == addressResponse.district
        addressEntity.detail == addressResponse.detail
    }

    def "TestMapResponseToEntity"() {
        given:
        def addressResponse = random.nextObject(AddressResponse)

        when:
        def addressEntity = ADDRESS_MAPPER.mapResponseToEntity(addressResponse)

        then:
        addressResponse.city == addressEntity.city
        addressResponse.district == addressEntity.district
        addressResponse.detail == addressEntity.detail
    }
}