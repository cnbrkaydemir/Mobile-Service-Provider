package com.mobile_service_provider.service;

import com.mobile_service_provider.dto.PackageDto;
import com.mobile_service_provider.dto.UsersDto;
import com.mobile_service_provider.model.UserGroup;
import com.mobile_service_provider.model.Users;

import java.util.List;

public interface UsersService {
    UsersDto createUser(Users user);
    List<UsersDto> getAllUsers();

    UsersDto getUser(int id);

    boolean deleteUser(int id);

    boolean registerPackage(int userId, int packageId);

    List<PackageDto> getUserPackages(int id);

    boolean checkStudent(boolean isStudent, Users user);

    boolean switchUserGroup(UserGroup userGroup, Users user);



}
