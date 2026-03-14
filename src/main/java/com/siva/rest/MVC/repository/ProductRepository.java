package com.siva.rest.MVC.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.siva.rest.MVC.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}