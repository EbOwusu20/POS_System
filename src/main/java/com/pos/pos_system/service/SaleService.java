package com.pos.pos_system.service;


import com.pos.pos_system.dto.SaleItemDTO;
import com.pos.pos_system.dto.SaleRequestDTO;
import com.pos.pos_system.dto.SaleResponseDTO;
import com.pos.pos_system.model.Product;
import com.pos.pos_system.model.Sale;
import com.pos.pos_system.model.SaleItem;
import com.pos.pos_system.repository.ProductRepository;
import com.pos.pos_system.repository.SaleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;

    public  SaleService(SaleRepository saleRepository, ProductRepository productRepository){
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;

    }

    //---------------------
    // CREATE / PROCESS SALE
    //---------------------


    @Transactional
    public SaleResponseDTO processSale(SaleRequestDTO request){
        List<SaleItem> saleItems = new ArrayList<>();
        double total = 0;

        for (SaleItemDTO itemDTO : request.getItems()){
            Product product = productRepository.findByBarcode(itemDTO.getBarcode()).orElseThrow(()-> new RuntimeException("Product not found: " + itemDTO.getBarcode()));


            //CHECK STOCK
            if(product.getQuantity() < itemDTO.getProductId()){
                throw new RuntimeException("Not Enough Stock for product: " + product.getName());
            }

            //DEDUCT STOCK
            product.setQuantity(product.getQuantity() - itemDTO.getQuantity());
            productRepository.save(product);


            // CREATE TOTAL;
            double itemTotal = itemDTO.getQuantity() * product.getPrice();
            total += itemTotal;


            //CREATE SALES ITEM;
            SaleItem saleItem = new SaleItem();
            saleItem.setProduct(product);
            saleItem.setQuantity(itemDTO.getQuantity());
            saleItem.setPrice(product.getPrice());

            saleItems.add(saleItem);

        }

        //CREATE SALE;

        Sale sale = new Sale();
        sale.setCustomerId(request.getCustomerId());
        sale.setPaymentMethod(request.getPaymentMethod());
        sale.setTotalAmount(total);
        sale.setItems(saleItems);


        //Link SaleItem -> sale
        for(SaleItem si : saleItems) {
            si.setSale(sale);
        }

        // Save Sale
        Sale savedSale = saleRepository.save(sale);

        //Return DTO
        return  mapToDTO(savedSale);



    }

    // GET ALL SALES

    public  List<SaleResponseDTO> getAllSales() {
        return  saleRepository.findAll().stream().map(this:: mapToDTO).collect(Collectors.toList());
    }

    //GET SALE BY ID

    public SaleResponseDTO getSaleById(Long id){
        Sale sale = saleRepository.findById(id).orElseThrow(()-> new RuntimeException("Sale not found with id: " + id));

        return mapToDTO(sale);
    }

    private SaleResponseDTO mapToDTO(Sale sale) {
        List<SaleItemDTO> items = sale.getItems().stream().map(item ->
                {
                    SaleItemDTO dto = new SaleItemDTO();
                    dto.setName(item.getProduct().getName());
                    dto.setQuantity(item.getQuantity());
                    dto.setPrice(item.getPrice());
                    return dto;
                }
                ).collect(Collectors.toList());
        return  new SaleResponseDTO(
                sale.getId(),
                sale.getSaleDate(),
                sale.getTotalAmount(),
                sale.getPaymentMethod(),
                items

        );
    }

    //DELETE SALE

    public void deleteSale(Long id){
        if(!saleRepository.existsById(id)){
            throw new RuntimeException("Sale not found with id: " + id);
        }
        saleRepository.deleteById(id);
    }











}