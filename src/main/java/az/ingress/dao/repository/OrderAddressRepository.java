package az.ingress.dao.repository;

import az.ingress.dao.entity.OrderAddressEntity;
import az.ingress.dao.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderAddressRepository extends JpaRepository<OrderAddressEntity, Long> {
}
