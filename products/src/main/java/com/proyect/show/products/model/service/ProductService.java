package com.proyect.show.products.model.service;

import java.util.List;

import com.commons.commons.entity.Product;

public interface ProductService {
    
    public List<Product> findAll();
    public Product findById(Long id);

    public Product save(Product product);

    public void deleteById(Long id);
}
