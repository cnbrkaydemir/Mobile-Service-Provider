package com.mobile_service_provider.service.impl;

import com.mobile_service_provider.model.Credit;
import com.mobile_service_provider.model.PackageInfo;
import com.mobile_service_provider.model.PackageTypeCredit;
import com.mobile_service_provider.repository.PackageTypeCreditRepository;
import com.mobile_service_provider.service.PackageTypeCreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class PackageTypeCreditServiceImpl implements PackageTypeCreditService {

    private final PackageTypeCreditRepository packageTypeCreditRepository;


    @Override
    public void fillPackageTypeCredits(PackageInfo pack, Credit credit) {
        PackageTypeCredit packageTypeCredit = new PackageTypeCredit();
        packageTypeCredit.setCreatedDate(new Date());
        packageTypeCredit.setCreatedBy("admin");
        packageTypeCredit.setCredits(credit);
        packageTypeCredit.setPackageInfo(pack);
        packageTypeCredit.setCreditType(credit.getCreditType());
        packageTypeCredit.setCreditAmount(credit.getCreditAmount());
        packageTypeCreditRepository.save(packageTypeCredit);

    }
}
