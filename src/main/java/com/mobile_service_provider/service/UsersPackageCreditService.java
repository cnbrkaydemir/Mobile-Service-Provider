package com.mobile_service_provider.service;

import com.mobile_service_provider.model.CreditType;
import com.mobile_service_provider.model.PackageInfo;
import com.mobile_service_provider.model.Users;
import com.mobile_service_provider.model.UsersPackageCredit;

import java.math.BigDecimal;
import java.util.List;

public interface UsersPackageCreditService {

    void fillUserPackageCredits(Users user, PackageInfo packageInfo);


    void updateUserCredits(Users user, CreditType creditType, BigDecimal amount);

    List<UsersPackageCredit> getUserCredits(Users user);

}
