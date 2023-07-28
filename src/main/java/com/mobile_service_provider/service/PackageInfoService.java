package com.mobile_service_provider.service;

import com.mobile_service_provider.dto.CreditDto;
import com.mobile_service_provider.dto.PackageDto;
import com.mobile_service_provider.dto.UsersDto;
import com.mobile_service_provider.model.Credit;
import com.mobile_service_provider.model.PackageInfo;
import com.mobile_service_provider.model.Users;

import java.util.List;

public interface PackageInfoService {
    PackageDto createPackage(PackageInfo packageInfo);
    List<PackageDto> getAllPackages();

    PackageDto getPackage(int id);

    boolean deletePackage(int id);

    boolean fillCredits(int id , List<Credit> credits);

    List<CreditDto> getPackageCredit (int id);


    boolean checkActive(PackageInfo packageInfo);

}
