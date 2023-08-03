package com.mobile_service_provider.controller;


import com.mobile_service_provider.dto.*;
import com.mobile_service_provider.model.Users;
import com.mobile_service_provider.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsersController {


    private final UsersService usersService;



    @PostMapping("/register")
    public ResponseEntity<UsersDto> createUser(@RequestBody Users newUser){
        UsersDto usersDto =  usersService.createUser(newUser);
        return ResponseEntity.ok(usersDto);
    }



    @GetMapping("/get/{id}")
    public ResponseEntity<UsersDto> getUser(@PathVariable int id){
        UsersDto usersDto = usersService.getUser(id);
        return ResponseEntity.ok(usersDto);
    }


    @GetMapping("/get_all")
    public ResponseEntity<List<UsersDto>> getAllUsers(){
        List<UsersDto> allUsers = usersService.getAllUsers();

        return ResponseEntity.ok(allUsers);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable int id){
        Boolean isDeleted = usersService.deleteUser(id);

        return ResponseEntity.ok(isDeleted);
    }

    @PostMapping("/register_package")
    public ResponseEntity<Boolean> registerPackage(@RequestParam int userId, @RequestParam int packageId){
        Boolean  register =  usersService.registerPackage(userId, packageId);
        return ResponseEntity.ok(register);
    }

    @GetMapping("/get_package")
    public ResponseEntity<List<PackageDto>> getPackages(@RequestParam int userId){
        List<PackageDto> packages = usersService.getUserPackages(userId);
        return ResponseEntity.ok(packages);
    }


    @PostMapping("/update_credits")
    public ResponseEntity<List<CreditDto>> updatePackages(@RequestBody UsersPackageCreditDto userCredit){
        usersService.updateCredits(userCredit);

        UserPackageDto updated =  new UserPackageDto(userCredit.getUserId(), userCredit.getPackageId());

        return ResponseEntity.ok(usersService.getRemainingCredits(updated));
    }

    @GetMapping("/get_credits")
    public ResponseEntity<List<CreditDto>> getCredits(@RequestBody UserPackageDto userPackageDto){
        return ResponseEntity.ok(usersService.getRemainingCredits(userPackageDto));
    }

}

