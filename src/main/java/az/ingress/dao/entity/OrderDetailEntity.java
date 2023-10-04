package az.ingress.dao.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import static az.ingress.dao.entity.OrderDetailEntity.TABLE_NAME;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@FieldDefaults(level = PRIVATE)
public class OrderDetailEntity {
    final static String TABLE_NAME = "order_details";
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    Long productId;
    Integer quantity;
    BigDecimal totalAmount;

    @OneToOne(fetch = LAZY)
    @MapsId
    @JoinColumn(name = "id")
    @JsonBackReference
    @ToString.Exclude
    OrderEntity order;
}
