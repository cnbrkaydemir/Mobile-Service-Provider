package com.mobile_service_provider.service.impl;


import com.mobile_service_provider.dto.UsersPackageCreditDto;
import com.mobile_service_provider.model.PackageInfo;
import com.mobile_service_provider.model.Users;
import com.mobile_service_provider.model.UsersPackageCredit;
import com.mobile_service_provider.repository.UsersPackageCreditRepository;
import com.mobile_service_provider.service.UsersPackageCreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @Override
    public void updateUserCredits(Users user, PackageInfo pack,UsersPackageCreditDto userCredit) {
        List<UsersPackageCredit> usersCredit = usersPackageCreditRepository.findByUsersAndPackageInfo(user, pack);

        usersCredit.forEach( credit -> {
            if(credit.getCreditType() == userCredit.getCreditType()){
                credit.setCreditAmount(credit.getCreditAmount().subtract(userCredit.getCreditAmount()));
                usersPackageCreditRepository.save(credit);

                System.out.println("Updated credits successfully");
            }
        });


    }

    @Override
    public List<UsersPackageCredit> getUserCredits(Users user,PackageInfo packageInfo){
        return usersPackageCreditRepository.findByUsersAndPackageInfo(user, packageInfo);
    }

}
