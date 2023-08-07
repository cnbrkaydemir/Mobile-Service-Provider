package com.mobile_service_provider.controller;

import com.mobile_service_provider.config.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<String>  token(Authentication authentication){
        String token = tokenService.generateJwt(authentication);
        return ResponseEntity.ok(token);
    }


}
