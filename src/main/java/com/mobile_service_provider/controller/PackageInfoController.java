package com.mobile_service_provider.controller;

import com.mobile_service_provider.dto.PackageDto;
import com.mobile_service_provider.dto.UsersDto;
import com.mobile_service_provider.model.PackageInfo;
import com.mobile_service_provider.model.Users;
import com.mobile_service_provider.service.PackageInfoService;
import com.mobile_service_provider.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/package")
public class PackageInfoController {

    private final PackageInfoService packageInfoService;

    @Autowired
    public PackageInfoController(PackageInfoService packageInfoService) {
        this.packageInfoService = packageInfoService;
    }

    @PostMapping("/create")
    public ResponseEntity<PackageInfo> createPackage(@RequestBody PackageInfo packageInfo){
        PackageInfo pack =  packageInfoService.createPackage(packageInfo);
        return ResponseEntity.ok(pack);
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<PackageDto> getPackage(@PathVariable int id){
        PackageDto packageDto = packageInfoService.getPackage(id);
        return ResponseEntity.ok(packageDto);
    }


    @GetMapping("/get_all")
    public ResponseEntity<List<PackageDto>> getAllPackages(){
        List<PackageDto> allPackages = packageInfoService.getAllPackages();

        return ResponseEntity.ok(allPackages);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deletePackage(@PathVariable int id){
        Boolean isDeleted = packageInfoService.deletePackage(id);

        return ResponseEntity.ok(isDeleted);
    }

}
