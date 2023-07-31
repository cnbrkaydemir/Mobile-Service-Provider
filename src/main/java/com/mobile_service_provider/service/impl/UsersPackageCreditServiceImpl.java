package com.mobile_service_provider.service.impl;


import com.mobile_service_provider.dto.CreditDto;
import com.mobile_service_provider.model.CreditType;
import com.mobile_service_provider.model.PackageInfo;
import com.mobile_service_provider.model.Users;
import com.mobile_service_provider.model.UsersPackageCredit;
import com.mobile_service_provider.repository.UsersPackageCreditRepository;
import com.mobile_service_provider.service.UsersPackageCreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public void updateUserCredits(Users user, CreditType creditType, BigDecimal amount) {
        user.getCredits().forEach( credit -> {
            Optional<UsersPackageCredit> target = usersPackageCreditRepository.findById(credit.getUserPackageId());
            if(target.isPresent() && target.get().getCreditType() == creditType){
                credit.setCreditAmount(credit.getCreditAmount().subtract(amount));
                usersPackageCreditRepository.save(credit);

                System.out.println("Updated credits successfully");
            }
        });

    }


    public List<UsersPackageCredit> getUserCredits(Users user){
        return usersPackageCreditRepository.findUsersPackageCreditByUsers(user);
    }

}
