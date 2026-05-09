package com.pos.pos_system.repository;


import com.pos.pos_system.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query("SELECT  SUM(s.totalAmount) FROM Sale s")
    Double getTotalSales();

    List<Sale> findByCustomerId(Long customerId);
}
