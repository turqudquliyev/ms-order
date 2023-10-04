package az.ingress.dao.entity;

import az.ingress.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

import static az.ingress.dao.entity.OrderEntity.TABLE_NAME;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
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
public class OrderEntity {
    final static String TABLE_NAME = "orders";
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    Long userId;
    @Enumerated(STRING)
    OrderStatus status;

    @OneToOne(cascade = ALL, fetch = LAZY, mappedBy = "order")
    @ToString.Exclude
    OrderDetailEntity orderDetail;

    @OneToOne(cascade = ALL, fetch = LAZY, mappedBy = "order")
    @ToString.Exclude
    OrderAddressEntity address;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        OrderEntity entity = (OrderEntity) o;
        return getId() != null && Objects.equals(getId(), entity.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}