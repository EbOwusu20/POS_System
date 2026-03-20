package com.pos.pos_system.controller;

import com.pos.pos_system.model.Product;
import com.pos.pos_system.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service){
        this.service = service;
    }

    @PostMapping
    public Product create(@RequestBody Product product){
        return  service.save(product);
    }
    @GetMapping
    public List<Product> getAll(){
        return service.getAll();
    }
    @GetMapping("/barcode/{barcode}")
    public Product getByBarcode(@PathVariable String barcode){
        return service.getByBarcode(barcode);
    }
}
