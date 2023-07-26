package com.mobile_service_provider.service.impl;

import com.mobile_service_provider.dto.UsersDto;
import com.mobile_service_provider.model.UserGroup;
import com.mobile_service_provider.model.Users;
import com.mobile_service_provider.repository.UsersRepository;
import com.mobile_service_provider.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;

    @Override
    public Users createUser(Users newUser) {
        newUser.setCreatedBy("admin");
        newUser.setCreatedDate(new Date());
        newUser.setUserGroup(UserGroup.BRONZE);
        newUser.setStatus("active");
        newUser.setStudent(false);

        return usersRepository.save(newUser);
    }

    @Override
    public List<UsersDto> getAllUsers() {
        List<Users> users = usersRepository.findAll();

        List<UsersDto> usersDto = users.stream().map( user -> modelMapper.map(user, UsersDto.class)).toList();

        return usersDto;
    }

    @Override
    public UsersDto getUser(int id) {
        Optional<Users> target = usersRepository.findById(id);

        if(target.isPresent())
            return modelMapper.map(target.get(), UsersDto.class);

        return null;
    }

    @Override
    public boolean deleteUser(int id) {

        Optional<Users> target = usersRepository.findById(id);

        if(target.isPresent()){
            usersRepository.deleteById(id);
            return true;
        }

        return false;
    }

}
