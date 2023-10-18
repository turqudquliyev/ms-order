package az.ingress.controller

import az.ingress.exception.GlobalExceptionHandler
import az.ingress.model.response.AddressResponse
import az.ingress.model.response.OrderResponse
import az.ingress.service.abstraction.OrderService
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static az.ingress.model.enums.OrderStatus.CREATED
import static java.math.BigDecimal.TEN
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

class InternalOrderControllerTest extends Specification {
    OrderService orderService
    InternalOrderController internalOrderController
    MockMvc mockMvc

    def setup() {
        orderService = Mock()
        internalOrderController = new InternalOrderController(orderService)
        mockMvc = MockMvcBuilders.standaloneSetup(internalOrderController)
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build()
    }

    def "TestGetByIdIn success case"() {
        given:
        def ids = [1L]
        def url = "/internal/v1/orders"
        def orderResponse = new OrderResponse(1L, 2L, CREATED, 3L, 4, TEN,
                new AddressResponse("City", "District", "Detail"))
        def expectedResponse = '''
                                            [
                                                {
                                                      "id": 1,
                                                      "userId": 2,
                                                      "status": "CREATED",
                                                      "productId": 3,
                                                      "quantity": 4,
                                                      "totalAmount": 10.00,
                                                      "address": {
                                                        "city": "City",
                                                        "district": "District",
                                                        "detail": "Detail"
                                                      }
                                                }
                                            ]
                                      '''
        when:
        def jsonResponse = mockMvc.perform(get(url)
                .contentType(APPLICATION_JSON)
                .param("ids", ids[0].toString())
        ).andReturn()

        then:
        1 * orderService.getOrderByIdIn(ids) >> [orderResponse]
        JSONAssert.assertEquals(expectedResponse.toString(), jsonResponse.response.contentAsString.toString(), true)
    }
}
