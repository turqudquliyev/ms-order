package az.ingress.controller

import az.ingress.exception.GlobalExceptionHandler
import az.ingress.model.enums.OrderStatus
import az.ingress.model.request.AddressRequest
import az.ingress.model.request.OrderRequest
import az.ingress.model.response.AddressResponse
import az.ingress.model.response.OrderResponse
import az.ingress.service.abstraction.OrderService
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static az.ingress.model.constant.HeaderConstant.USER_ID
import static java.math.BigDecimal.TEN
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

class OrderControllerTest extends Specification {
    OrderService orderService
    OrderController orderController
    MockMvc mockMvc

    def setup() {
        orderService = Mock()
        orderController = new OrderController(orderService)
        mockMvc = MockMvcBuilders.standaloneSetup(orderController)
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build()
    }

    def "TestCreateOrder success case"() {
        given:
        def userId = 2L
        def url = "/v1/orders"
        def orderRequest = new OrderRequest(3L, 4,
                new AddressRequest("City", "District", "Detail"))
        def orderResponse = new OrderResponse(1L, 2L, OrderStatus.CREATED, 3L, 4, TEN,
                new AddressResponse("City", "District", "Detail"))
        def jsonRequest = '''
                                        {
                                          "productId": 3,
                                          "quantity": 4,
                                          "address": {
                                            "city": "City",
                                            "district": "District",
                                            "detail": "Detail"
                                          }
                                        }
                                '''
        def expectedResponse = '''
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
                                      '''
        when:
        def jsonResponse = mockMvc.perform(
                post(url)
                        .contentType(APPLICATION_JSON)
                        .header(USER_ID, userId.toString())
                        .content(jsonRequest)
        ).andReturn()

        then:
        1 * orderService.createOrder(userId, orderRequest) >> orderResponse
        jsonResponse.response.status == CREATED.value()
        JSONAssert.assertEquals(expectedResponse.toString(), jsonResponse.response.contentAsString.toString(), true)
    }

    def "TestCancelOrder success case"() {
        given:
        def id = 1L
        def url = "/v1/orders/$id/cancel"

        when:
        def jsonResponse = mockMvc.perform(
                patch(url).contentType(APPLICATION_JSON)
        ).andReturn()

        then:
        1 * orderService.cancelOrder(id)
        jsonResponse.response.status == NO_CONTENT.value()
    }
}
