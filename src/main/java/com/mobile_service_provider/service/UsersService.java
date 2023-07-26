package com.mobile_service_provider.service;

import com.mobile_service_provider.dto.UsersDto;
import com.mobile_service_provider.model.Users;

import java.util.List;

public interface UsersService {
    Users createUser(Users user);
    List<UsersDto> getAllUsers();

    UsersDto getUser(int id);

    boolean deleteUser(int id);


}
