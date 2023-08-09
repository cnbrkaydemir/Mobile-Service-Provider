package com.mobile_service_provider.dto;

import com.mobile_service_provider.model.Credit;
import com.mobile_service_provider.model.CreditType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CreditDto implements Serializable {

    private CreditType creditType;


    private BigDecimal creditAmount;


    public static CreditDto of(Credit credit){
        CreditDto creditDto = new CreditDto();
        creditDto.setCreditAmount(credit.getCreditAmount());
        creditDto.setCreditType(credit.getCreditType());
        return creditDto;
    }

}
