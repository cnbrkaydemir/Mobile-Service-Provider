package com.mobile_service_provider.service;

import com.mobile_service_provider.dto.*;
import com.mobile_service_provider.model.CreditType;
import com.mobile_service_provider.model.UserGroup;
import com.mobile_service_provider.model.Users;
import org.apache.catalina.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UsersService {
    UsersDto createUser(Users user);
    List<UsersDto> getAllUsers();

    UsersDto getUser(int id);

    boolean deleteUser(int id);

    boolean registerPackage(int userId, int packageId);

    List<PackageDto> getUserPackages(int id);

    boolean checkStudent(boolean isStudent, Users user);

    boolean switchUserGroup(UserGroup userGroup, Users user);

    void updateCredits(UsersPackageCreditDto userCredits);
    List<CreditDto> getRemainingCredits(UserPackageDto userPackageDto);

    Users getById(int id);

    Optional<Users> findByUserEmail(String email);



}
