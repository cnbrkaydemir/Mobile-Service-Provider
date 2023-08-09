package com.mobile_service_provider.dto;

import com.mobile_service_provider.model.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PackageDto  implements Serializable {

    private int id;

    private String name;

    private byte duration;

    private BigDecimal price;

    private PackageGroup packageGroup;


    public static PackageDto of(PackageInfo packageInfo){
        PackageDto packageDto = new PackageDto();
        packageDto.setDuration(packageInfo.getDuration());
        packageDto.setPackageGroup(packageInfo.getPackageGroup());
        packageDto.setId(packageInfo.getId());
        packageDto.setPrice(packageInfo.getPrice());
        return packageDto;
    }


}
