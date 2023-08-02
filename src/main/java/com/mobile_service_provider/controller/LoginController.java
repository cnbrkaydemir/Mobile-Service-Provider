package com.mobile_service_provider.controller;

import com.mobile_service_provider.dto.UsersDto;
import com.mobile_service_provider.model.Users;
import com.mobile_service_provider.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private  final UsersService usersService;
    @GetMapping("/login")
    public ResponseEntity<Users> getUserDetailsAfterLogin(Principal user){
        Optional<Users> loggedUser=usersService.findByUserEmail(user.getName());

        if(loggedUser.isPresent()){
            return ResponseEntity.ok(loggedUser.get());
        }

        else{
            System.out.println("Cannot find user wth given credentials.");
            return null;
        }

    }

}
