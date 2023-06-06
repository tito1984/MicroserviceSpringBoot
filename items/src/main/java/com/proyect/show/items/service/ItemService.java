package com.proyect.show.items.service;

import java.util.List;

import com.commons.commons.entity.Product;
import com.proyect.show.items.models.Item;

public interface ItemService {
    
    public List<Item> findAll();
    public Item findById(Long id, Integer quantity);

    public Product save(Product product);
    public Product update(Product product, Long id);
    public void delete(Long id);
}
