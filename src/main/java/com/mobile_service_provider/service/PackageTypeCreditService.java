package com.mobile_service_provider.service;

import com.mobile_service_provider.model.Credit;
import com.mobile_service_provider.model.PackageInfo;

public interface PackageTypeCreditService {

    void fillPackageTypeCredits(PackageInfo pack, Credit credit);

}
