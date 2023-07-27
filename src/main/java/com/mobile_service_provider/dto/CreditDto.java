package com.mobile_service_provider.dto;

import com.mobile_service_provider.model.CreditType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreditDto {

    private CreditType creditType;


    private BigDecimal creditAmount;

}
