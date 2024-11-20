package com.fransoufil.ms_orders.repositories;

import com.fransoufil.ms_orders.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findById(Long id);

    boolean existsById(Long id);
}
