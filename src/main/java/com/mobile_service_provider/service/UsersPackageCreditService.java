package com.mobile_service_provider.service;

import com.mobile_service_provider.model.PackageInfo;
import com.mobile_service_provider.model.Users;

public interface UsersPackageCreditService {

    void fillUserPackageCredits(Users user, PackageInfo packageInfo);
}
