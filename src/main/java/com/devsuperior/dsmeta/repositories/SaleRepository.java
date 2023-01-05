package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.information.SaleReportInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT obj.id AS id, obj.date AS date, obj.amount AS amount, obj.seller.name AS sellerName, SUM(obj.amount) FROM Sale obj " +
            "WHERE UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name ,'%')) AND obj.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY obj.id, obj.date, obj.amount, obj.seller.name " +
            "ORDER BY obj.date DESC")
    List<SaleReportInformation> getReport(LocalDate minDate, LocalDate maxDate, String name);

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO(obj.seller.name, SUM(obj.amount)) "
            + "FROM Sale obj "
            + "WHERE obj.date BETWEEN :dateMin AND :dateMax "
            + "GROUP BY obj.seller.name" )
    Page<SaleSummaryDTO> getSummary(LocalDate dateMin, LocalDate dateMax, Pageable pageable);
}


