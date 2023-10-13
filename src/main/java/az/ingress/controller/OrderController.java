package az.ingress.controller;

import az.ingress.model.request.OrderRequest;
import az.ingress.model.response.OrderResponse;
import az.ingress.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import static az.ingress.model.constant.HeaderConstant.USER_ID;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OrderController {
    OrderService orderService;

    @PostMapping
    @ResponseStatus(CREATED)
    public OrderResponse createOrder(@RequestHeader(USER_ID) @NotNull Long userId,
                                     @RequestBody @Valid OrderRequest orderRequest) {
        return orderService.createOrder(userId, orderRequest);
    }

    @PatchMapping("/{id}/cancel")
    @ResponseStatus(NO_CONTENT)
    public void cancelOrder(@PathVariable @NotNull Long id) {
        orderService.cancelOrder(id);
    }
}