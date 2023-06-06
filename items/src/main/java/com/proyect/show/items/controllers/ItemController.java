package com.proyect.show.items.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.commons.commons.entity.Product;
import com.proyect.show.items.models.Item;
import com.proyect.show.items.service.ItemService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;


@RefreshScope
@RestController
public class ItemController {

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

    @Autowired
    private Environment environment;
    
    @Autowired
    @Qualifier("serviceFeign")
    private ItemService itemService;

    @Value("${configuration.texto}")
    private String texto;

    @GetMapping("/list")
    public List<Item> listAll() {
        return itemService.findAll();
    }

    @GetMapping("/{id}/quantity/{quantity}")
    public Item detail(@PathVariable Long id, @PathVariable Integer quantity) {
        return circuitBreakerFactory.create("items")
                    .run(() -> itemService.findById(id, quantity), error -> alternativeMethod(id, quantity));
    }

    @CircuitBreaker(name = "items", fallbackMethod = "alternativeMethod")
    @GetMapping("/{id}/quantity2/{quantity}")
    public Item detail2(@PathVariable Long id, @PathVariable Integer quantity) {
        return itemService.findById(id, quantity);
    }

    @TimeLimiter(name = "items", fallbackMethod = "alternativeMethod2")
    @GetMapping("/{id}/quantity3/{quantity}")
    public CompletableFuture<Item> detail3(@PathVariable Long id, @PathVariable Integer quantity) {
        return CompletableFuture.supplyAsync(() -> itemService.findById(id, quantity));
    }

    private Item alternativeMethod(Long id, Integer quantity) {
        Item item = new Item();
        Product product = new Product();
        item.setQuantity(quantity);
        product.setId(id);
        product.setName("Sony Camera");
        product.setPrice(800.00);
        item.setProduct(product);
        return item;
    }

    private CompletableFuture<Item> alternativeMethod2(Long id, Integer quantity) {
        Item item = new Item();
        Product product = new Product();
        item.setQuantity(quantity);
        product.setId(id);
        product.setName("Sony Camera");
        product.setPrice(800.00);
        item.setProduct(product);
        return CompletableFuture.supplyAsync(() -> item);
    }

    @GetMapping("/get-config")
    public ResponseEntity<?> getConfig(@Value("${server.port}") String port) {

        Map<String, String> json = new HashMap<>();
        if(environment.getActiveProfiles().length>0 && environment.getActiveProfiles()[0].equals("dev")) {
            json.put("autor.name", environment.getProperty("Configuration.autor.name"));
            json.put("autor.email", environment.getProperty("Configuration.autor.email"));
        }
        json.put("texto", texto);
        json.put("port", port);

        return new ResponseEntity<Map<String,String>>(json, HttpStatus.OK);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        return itemService.save(product);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Product update(@RequestBody Product product, @PathVariable Long id) {
        return itemService.update(product, id);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        itemService.delete(id);
    }

}
