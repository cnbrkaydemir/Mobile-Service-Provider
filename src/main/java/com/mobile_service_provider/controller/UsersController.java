package com.mobile_service_provider.controller;

import com.mobile_service_provider.dto.UsersDto;
import com.mobile_service_provider.model.Users;
import com.mobile_service_provider.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UsersController {


    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/create")
    public ResponseEntity<Users> createUser(@RequestBody Users newUser){
        Users user =  usersService.createUser(newUser);
        return ResponseEntity.ok(user);
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



}
