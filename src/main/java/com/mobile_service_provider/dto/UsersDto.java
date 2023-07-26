package com.mobile_service_provider.dto;

import com.mobile_service_provider.model.PackageInfo;
import com.mobile_service_provider.model.UserGroup;
import com.mobile_service_provider.model.UsersPackageCredit;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class UsersDto {

    private int id;

    private String name;

    private String phoneNumber;

    private String email;



}
