package com.proyect.show.items.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.commons.commons.entity.Product;
import com.proyect.show.items.clients.ProductClientRest;
import com.proyect.show.items.models.Item;

@Service("serviceFeign")
public class ItemServiceFeign implements ItemService {

    @Autowired
    private ProductClientRest productClientRest;

    @Override
    public List<Item> findAll() {
        return productClientRest.listAll().stream().map(product -> new Item(product, 1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer quantity) {
        if (id == 2) {
            throw new ResourceAccessException(null);
        }
        return new Item(productClientRest.detail(id), quantity);
    }

    @Override
    public Product save(Product product) {
        return productClientRest.create(product);
    }

    @Override
    public Product update(Product product, Long id) {
        return productClientRest.update(product, id);
    }

    @Override
    public void delete(Long id) {
        productClientRest.delete(id);
    }
    
}
