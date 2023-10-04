package az.ingress.controller;

import az.ingress.model.request.CreateOrderRequest;
import az.ingress.service.IOrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OrderController {
    IOrderService service;

    @PostMapping
    public Long createOrder(@RequestHeader("User-Id") Long userId,
                            @RequestBody CreateOrderRequest orderRequest) {
        return service.createOrder(userId, orderRequest);
    }

    @PatchMapping("/{id}/cancel")
    public void cancelOrder(@PathVariable Long id) {
        service.cancelOrder(id);
    }
}