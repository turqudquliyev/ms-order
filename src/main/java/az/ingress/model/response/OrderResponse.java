package az.ingress.model.response;

import az.ingress.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = PRIVATE)
public class OrderResponse {
    Long id;
    Long userId;
    OrderStatus status;
    Long productId;
    Integer quantity;
    BigDecimal totalAmount;
    AddressResponse address;
}