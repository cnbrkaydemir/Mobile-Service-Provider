package com.mobile_service_provider.service.impl;

import com.mobile_service_provider.dto.PackageDto;
import com.mobile_service_provider.model.PackageInfo;
import com.mobile_service_provider.model.Users;
import com.mobile_service_provider.repository.PackageInfoRepository;
import com.mobile_service_provider.service.PackageInfoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PackageInfoServiceImpl implements PackageInfoService {

    private final PackageInfoRepository packageInfoRepository;

    private final ModelMapper modelMapper;

    @Override
    public PackageInfo createPackage(PackageInfo packageInfo) {
        packageInfo.setCreatedDate(new Date());
        packageInfo.setCreatedBy("admin");
        packageInfo.setStartDate(new Date());
        packageInfo.setActive(true);

        return packageInfoRepository.save(packageInfo);
    }

    @Override
    public List<PackageDto> getAllPackages() {
        List<PackageInfo> packages = packageInfoRepository.findAll();

        List<PackageDto> packageDtos= packages.stream().map(pack -> modelMapper.map(pack, PackageDto.class)).toList();

        return packageDtos;
    }

    @Override
    public PackageDto getPackage(int id) {
        Optional<PackageInfo> packageInfo = packageInfoRepository.findById(id);

        if(packageInfo.isPresent())
            return modelMapper.map(packageInfo.get(), PackageDto.class);

        return null;

    }

    @Override
    public boolean deletePackage(int id) {
        Optional<PackageInfo> target = packageInfoRepository.findById(id);

        if(target.isPresent()){
            packageInfoRepository.deleteById(id);
            return true;
        }

        return false;

    }
}
