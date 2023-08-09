package com.mobile_service_provider.dto;

import com.mobile_service_provider.model.PackageInfo;
import com.mobile_service_provider.model.UserGroup;
import com.mobile_service_provider.model.Users;
import com.mobile_service_provider.model.UsersPackageCredit;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
public class UsersDto implements Serializable {

    private int id;

    private String name;

    private String phoneNumber;

    private String email;


    public static UsersDto of(Users user){
        UsersDto usersDto = new UsersDto();
        usersDto.setEmail(user.getEmail());
        usersDto.setName(user.getName());
        usersDto.setId(user.getId());
        usersDto.setPhoneNumber(user.getPhoneNumber());
        return usersDto;
    }


}
