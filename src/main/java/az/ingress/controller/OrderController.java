package az.ingress.controller;

import az.ingress.model.request.CreateOrderRequest;
import az.ingress.model.response.OrderResponse;
import az.ingress.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import static az.ingress.model.constant.HeaderConstant.USER_ID;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OrderController {
    OrderService orderService;

    @PostMapping
    public OrderResponse createOrder(@RequestHeader(USER_ID) Long userId,
                                     @RequestBody CreateOrderRequest orderRequest) {
        return orderService.createOrder(userId, orderRequest);
    }

    @PatchMapping("/{id}/cancel")
    public void cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
    }
}