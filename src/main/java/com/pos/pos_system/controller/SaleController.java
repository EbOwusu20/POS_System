package com.pos.pos_system.controller;


import com.pos.pos_system.dto.SaleRequestDTO;
import com.pos.pos_system.dto.SaleResponseDTO;
import com.pos.pos_system.service.SaleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {
    private  final SaleService saleService;


    public SaleController(SaleService saleService){
        this.saleService = saleService;
    }

    // CREATE SALE
    @PostMapping
    public SaleResponseDTO createSale(@RequestBody SaleRequestDTO request){
        return saleService.processSale(request);
    }

    //GET ALL SALES
    @GetMapping
    public List<SaleResponseDTO> getAllSales(){
        return saleService.getAllSales();
    }

    //GET SALE BY ID
    @GetMapping("/{id}")
    public SaleResponseDTO getSaleById(@PathVariable Long id){
        return  saleService.getSaleById(id);
    }

    // DELETE SALE
    @DeleteMapping("/{id}")
    public String deleteSale(@PathVariable Long id){
        saleService.deleteSale(id);
        return "Sale deleted successfully";
    }



}


