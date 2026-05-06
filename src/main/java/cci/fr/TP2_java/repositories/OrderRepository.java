package cci.fr.TP2_java.repositories;

import cci.fr.TP2_java.entities.Order;
import cci.fr.TP2_java.entities.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);
}