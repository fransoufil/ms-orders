package com.fransoufil.ms_orders.repositories;

import com.fransoufil.ms_orders.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
