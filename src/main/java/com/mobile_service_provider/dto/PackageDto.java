package com.mobile_service_provider.dto;

import com.mobile_service_provider.model.*;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
public class PackageDto {

    private int id;

    private byte duration;

    private BigDecimal price;

    private boolean isActive;

    private PackageGroup packageGroup;

}
