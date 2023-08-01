package com.mobile_service_provider.dto;

import com.mobile_service_provider.model.CreditType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class UsersPackageCreditDto implements Serializable {

    private int userId;
    private int packageId;
    private CreditType creditType;
    private BigDecimal creditAmount;

}
