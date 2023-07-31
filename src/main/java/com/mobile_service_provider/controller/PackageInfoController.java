package com.mobile_service_provider.controller;

import com.mobile_service_provider.dto.CreditDto;
import com.mobile_service_provider.dto.PackageDto;
import com.mobile_service_provider.dto.UsersDto;
import com.mobile_service_provider.model.Credit;
import com.mobile_service_provider.model.PackageInfo;
import com.mobile_service_provider.service.PackageInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/package")
@RequiredArgsConstructor
public class PackageInfoController {

    private final PackageInfoService packageInfoService;



    @PostMapping("/create")
    public ResponseEntity<PackageDto> createPackage(@RequestBody PackageInfo packageInfo){
        PackageDto pack =  packageInfoService.createPackage(packageInfo);
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


    @PostMapping("/fill_credits")
    public ResponseEntity<Boolean> fillCredits(@RequestParam int packageId, @RequestBody List<Credit> credits){
        Boolean filled = packageInfoService.fillCredits(packageId, credits);

        return ResponseEntity.ok(filled);
    }


    @GetMapping("/get_credits")
    public ResponseEntity<List<CreditDto>> getCredits(@RequestParam int packageId){
        List<CreditDto> credits = packageInfoService.getPackageCredit(packageId);
        return ResponseEntity.ok(credits);
    }


}
