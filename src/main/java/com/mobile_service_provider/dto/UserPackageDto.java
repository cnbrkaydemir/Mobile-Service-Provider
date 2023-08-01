package com.mobile_service_provider.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class UserPackageDto implements Serializable {

    private int userId;

    private int packageId;

    public UserPackageDto(int userId, int packageId) {
        this.userId = userId;
        this.packageId = packageId;
    }

}
