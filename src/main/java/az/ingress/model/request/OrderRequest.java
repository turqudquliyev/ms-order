package az.ingress.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = PRIVATE)
public class OrderRequest {
    @Valid
    @NotNull(message = "productId not be null!")
    @Min(1L)
    Long productId;
    @NotNull(message = "quantity not be null!")
    @Min(1)
    Integer quantity;
    @NotNull(message = "address not be empty!")
    AddressRequest address;
}