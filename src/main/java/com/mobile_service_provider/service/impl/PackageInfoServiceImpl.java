package com.mobile_service_provider.service.impl;

import com.mobile_service_provider.dto.CreditDto;
import com.mobile_service_provider.dto.PackageDto;
import com.mobile_service_provider.model.Credit;
import com.mobile_service_provider.model.PackageInfo;
import com.mobile_service_provider.repository.PackageInfoRepository;
import com.mobile_service_provider.service.CreditService;
import com.mobile_service_provider.service.PackageInfoService;
import com.mobile_service_provider.service.PackageTypeCreditService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class PackageInfoServiceImpl implements PackageInfoService {

    private final PackageInfoRepository packageInfoRepository;

    private final CreditService creditService;

    private final PackageTypeCreditService packageTypeCreditService;

    private final ModelMapper modelMapper;

    @Override
    @CacheEvict(value = "all_packages", allEntries = true)
    public PackageDto createPackage(PackageInfo packageInfo) {
        packageInfo.setCreatedDate(new Date());
        packageInfo.setCreatedBy("admin");
        packageInfo.setStartDate(new Date());
        this.checkActive(packageInfo);

        return modelMapper.map(packageInfoRepository.save(packageInfo), PackageDto.class);
    }

    @Override
    @Cacheable("all_packages")
    public List<PackageDto> getAllPackages() {
        List<PackageInfo> packages = packageInfoRepository.findAll();

        List<PackageDto> packageDtos= packages.stream().map(pack -> modelMapper.map(pack, PackageDto.class)).toList();

        return packageDtos;
    }

    @Override
    @Cacheable("packages")
    public PackageDto getPackage(int id) {
        Optional<PackageInfo> packageInfo = packageInfoRepository.findById(id);

        if(packageInfo.isPresent())
            return modelMapper.map(packageInfo.get(), PackageDto.class);

        return null;

    }

    @Override
    @CacheEvict(value = {"packages", "all_packages"}, allEntries = true)
    public boolean deletePackage(int id) {
        Optional<PackageInfo> target = packageInfoRepository.findById(id);

        if(target.isPresent()){
            target.get().setUsers(null);
            target.get().setCredits(null);
            target.get().setUsersPackageCredits(null);
            target.get().setPackageTypeCredits(null);
            packageInfoRepository.deleteById(id);
            return true;
        }

        return false;

    }



      public boolean fillCredits(int id , List<Credit> credits){
        Optional<PackageInfo> pack = packageInfoRepository.findById(id);


        if(pack.isPresent() && !credits.isEmpty()){

            credits.forEach(credit -> {
                credit.setPackageInfo(pack.get());
                pack.get().getCredits().add(credit);
                creditService.saveCredit(credit);
                packageTypeCreditService.fillPackageTypeCredits(pack.get(), credit);

            });

            packageInfoRepository.save(pack.get());
            return true;
        }

            return false;
      }

    @Override
    public boolean deleteCredits(int id) {
        Optional<PackageInfo> targetPackage = packageInfoRepository.findById(id);

        if(targetPackage.isPresent()){
            targetPackage.get().getCredits().forEach(credit-> creditService.deleteCredit(credit.getId()));
            return true;
        }
        return false;
    }

    @Override
      public List<CreditDto> getPackageCredit (int id){
        Optional<PackageInfo> pack = packageInfoRepository.findById(id);

        if(pack.isPresent()){
            return pack.get().getCredits().stream().map(p -> modelMapper.map(p, CreditDto.class)).toList();
        }

        return null;

      }

    @Override
    public boolean checkActive(PackageInfo packageInfo) {
        Date currentDate = new Date();

        if(currentDate.compareTo(packageInfo.getEndDate()) > 0 ){
            packageInfo.setActive(false);
            packageInfoRepository.save(packageInfo);
            return false;
        }
        else{
            packageInfo.setActive(true);
            packageInfoRepository.save(packageInfo);

            return true;
        }

    }





}
