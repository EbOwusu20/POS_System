package com.pos.pos_system.controller;


import com.pos.pos_system.model.Customer;
import com.pos.pos_system.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private  final CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;

    }

    // CREATE
    @PostMapping
    public Customer create (@RequestBody Customer customer){
        return customerService.create(customer);
    }

    //GET ALL
    @GetMapping
    public List<Customer> getAll(){
        return  customerService.getAll();
    }

    //GET BY ID
    @GetMapping("/{id}")
    public Customer getById(@PathVariable Long id){
        return customerService.getById(id);
    }

    //UPDATE
    @PutMapping("/{id}")
    public Customer update(@PathVariable Long id, @RequestBody Customer customer){
        return  customerService.update(id, customer);
    }

    //DELETE
    public String delete(@PathVariable Long id){
        customerService.delete(id);
        return  "Customer deleted successfully";
    }



}
