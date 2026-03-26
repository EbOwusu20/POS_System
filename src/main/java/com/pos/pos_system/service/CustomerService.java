package com.pos.pos_system.service;


import com.pos.pos_system.model.Customer;
import com.pos.pos_system.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }


    // CREATE
    public Customer create(Customer customer){
        return customerRepository.save(customer);
    }

    //GET ALL
    public List<Customer> getAll(){
        return customerRepository.findAll();
    }

    //GET BY ID
    public Customer getById(Long id){
        return  customerRepository.findById(id).orElseThrow(()-> new RuntimeException("Customer not found"));
    }

    //UPDATE
    public Customer update(Long id, Customer updateCustomer){
        Customer customer = getById(id);

        customer.setName(updateCustomer.getName());
        customer.setPhone(updateCustomer.getPhone());
        customer.setEmail(updateCustomer.getEmail());
        customer.setAddress(updateCustomer.getAddress());

        return  customerRepository.save(customer);
    }

    //DELETE
    public void delete(Long id){
        customerRepository.deleteById(id);
    }


}
