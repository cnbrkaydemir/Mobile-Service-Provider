package com.mobile_service_provider.service.impl;

import com.mobile_service_provider.model.PackageInfo;
import com.mobile_service_provider.model.Users;
import com.mobile_service_provider.model.UsersPackageCredit;
import com.mobile_service_provider.repository.UsersPackageCreditRepository;
import com.mobile_service_provider.service.UsersPackageCreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UsersPackageCreditServiceImpl implements UsersPackageCreditService {

    private final UsersPackageCreditRepository usersPackageCreditRepository;
    @Override
    public void fillUserPackageCredits(Users user, PackageInfo packageInfo) {

        if(!packageInfo.getCredits().isEmpty()){
            packageInfo.getCredits().forEach( credit -> {
                UsersPackageCredit usersPackageCredit = new UsersPackageCredit();
                usersPackageCredit.setCreatedDate(new Date());
                usersPackageCredit.setCreatedBy("admin");
                usersPackageCredit.setUsers(user);
                usersPackageCredit.setPackageInfo(packageInfo);
                usersPackageCredit.setCredits(credit);
                usersPackageCredit.setCreditType(credit.getCreditType());
                usersPackageCredit.setCreditAmount(credit.getCreditAmount());
                usersPackageCreditRepository.save(usersPackageCredit);
            });

        }




    }
}
