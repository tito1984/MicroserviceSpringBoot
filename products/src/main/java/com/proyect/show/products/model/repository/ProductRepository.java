package com.proyect.show.products.model.repository;

import org.springframework.data.repository.CrudRepository;

import com.commons.commons.entity.Product;

public interface ProductRepository extends CrudRepository<Product,Long> {
    
}
