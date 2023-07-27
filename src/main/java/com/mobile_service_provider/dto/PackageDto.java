package com.mobile_service_provider.dto;

import com.mobile_service_provider.model.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PackageDto {

    private int id;

    private String name;

    private byte duration;

    private BigDecimal price;

    private PackageGroup packageGroup;

}
