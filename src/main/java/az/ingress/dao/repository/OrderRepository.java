package az.ingress.dao.repository;

import az.ingress.dao.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByIdIn(List<Long> orderIds);
}
