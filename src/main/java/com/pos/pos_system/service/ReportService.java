package com.pos.pos_system.service;

import com.pos.pos_system.model.Sale;
import com.pos.pos_system.repository.SaleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    private final SaleRepository saleRepository;

    public ReportService(SaleRepository saleRepository){
        this.saleRepository = saleRepository;
    }

    // TOTAL REVENUE
    public  double getTotalSales(){
        Double total = saleRepository.getTotalSales();
        return total != null ? total : 0;
    }

    // TOTAL NUMBER OF SALES
    public Long getTotalTransactions(){
        return saleRepository.count();
    }

    // GET ALL SALES
    public List<Sale> getAllSales(){
        return saleRepository.findAll();
    }
}
