package com.pos.pos_system.service;


import com.pos.pos_system.model.Product;
import com.pos.pos_system.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository){
        this.repository = repository;
    }



    public Product save(Product product){
        return repository.save(product);
    }

    public List<Product> getAll(){
        return repository.findAll();
    }
    public Product getByBarcode(String barcode){
        return repository.findByBarcode(barcode).orElseThrow(() -> new RuntimeException("Product not found"));
    }


    public void deleteProduct(Long id) {
        if(!repository.existsById(id)){
            throw new RuntimeException("Product not found with ID " + id);
        }
        repository.deleteById(id);
    }

    public Product getProductById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }
}
