package com.mobile_service_provider.service;

import com.mobile_service_provider.dto.PackageDto;
import com.mobile_service_provider.dto.UsersDto;
import com.mobile_service_provider.model.PackageInfo;
import com.mobile_service_provider.model.Users;

import java.util.List;

public interface PackageInfoService {
    PackageInfo createPackage(PackageInfo packageInfo);
    List<PackageDto> getAllPackages();

    PackageDto getPackage(int id);

    boolean deletePackage(int id);

}
