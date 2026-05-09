package com.pos.pos_system.controller;

import com.pos.pos_system.model.Product;
import com.pos.pos_system.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
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
    // Delete by Id;
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        try {
            service.deleteProduct(id);
            return  ResponseEntity.ok("Product deleted successfully!");
        } catch (RuntimeException e ){
            return  ResponseEntity.status(404).body(e.getMessage());
        }

    }

    // Update by Id;
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updateProduct){
        Product existingProduct = service.getProductById(id);
        if (existingProduct == null) {
            return  ResponseEntity.notFound().build();
        }
        //Update fields
        existingProduct.setName(updateProduct.getName());
        existingProduct.setCategory(updateProduct.getCategory());
        existingProduct.setPrice(updateProduct.getPrice());
        existingProduct.setQuantity(updateProduct.getQuantity());
        existingProduct.setBarcode(updateProduct.getBarcode());

        Product savedProduct = service.save(existingProduct);
        return ResponseEntity.ok(savedProduct);
    }



}
