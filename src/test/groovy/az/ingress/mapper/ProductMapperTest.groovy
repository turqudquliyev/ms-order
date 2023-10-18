package az.ingress.mapper

import az.ingress.model.enums.OrderStatus
import az.ingress.model.request.OrderRequest
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import io.github.benas.randombeans.randomizers.misc.EnumRandomizer
import spock.lang.Specification

import static az.ingress.mapper.ProductMapper.PRODUCT_MAPPER

class ProductMapperTest extends Specification {
    EnhancedRandom random = EnhancedRandomBuilder
            .aNewEnhancedRandomBuilder()
            .randomize(OrderStatus.class, new EnumRandomizer<>(OrderStatus.class))
            .build()

    def "TestBuildProductRequest"() {
        given:
        def orderRequest = random.nextObject(OrderRequest)

        when:
        def productRequest = PRODUCT_MAPPER.buildProductRequest(orderRequest)

        then:
        productRequest.productId == orderRequest.productId
        productRequest.quantity == orderRequest.quantity
    }
}