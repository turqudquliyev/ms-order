package az.ingress.controller;

import az.ingress.dao.entity.OrderEntity;
import az.ingress.model.response.OrderResponse;
import az.ingress.service.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/v1/internal/orders")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class InternalOrderController {
    IOrderService service;

    @GetMapping
    public List<OrderResponse> getByIdIn(@RequestParam("ids") List<Long> orderIds) {
        return service.getByIdIn(orderIds);
    }
}