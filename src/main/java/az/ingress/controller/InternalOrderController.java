package az.ingress.controller;

import az.ingress.model.response.OrderResponse;
import az.ingress.service.abstraction.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/internal/v1/orders")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class InternalOrderController {
    OrderService orderService;

    @GetMapping
    public List<OrderResponse> getByIdIn(@RequestParam("ids") List<Long> orderIds) {
        return orderService.getOrderByIdIn(orderIds);
    }
}