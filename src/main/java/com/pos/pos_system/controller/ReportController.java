package com.pos.pos_system.controller;


import com.pos.pos_system.model.Sale;
import com.pos.pos_system.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService){
        this.reportService = reportService;
    }

    //TOTAL SALES AMOUNT
    @GetMapping("/total-sales")
    public double getTotalSales(){
        return reportService.getTotalSales();
    }

    // TOTAL TRANSACTIONS
    @GetMapping("/total-transactions")
    public Long getTotalTransactions(){
        return reportService.getTotalTransactions();
    }

    // ALL SALES
    @GetMapping("/all-sales")
    public List<Sale> getAllSales(){
        return  reportService.getAllSales();
    }




}
