package com.devsuperior.dsmeta.information;

import java.time.LocalDate;

public interface SaleReportInformation {

    Long getId();
    LocalDate getDate();
    Double getAmount();
    String getSellerName();

}
