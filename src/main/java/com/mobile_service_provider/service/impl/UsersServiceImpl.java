package com.mobile_service_provider.service.impl;

import com.mobile_service_provider.dto.CreditDto;
import com.mobile_service_provider.dto.PackageDto;
import com.mobile_service_provider.dto.UsersDto;
import com.mobile_service_provider.model.CreditType;
import com.mobile_service_provider.model.PackageInfo;
import com.mobile_service_provider.model.UserGroup;
import com.mobile_service_provider.model.Users;
import com.mobile_service_provider.repository.PackageInfoRepository;
import com.mobile_service_provider.repository.UsersRepository;
import com.mobile_service_provider.service.UsersPackageCreditService;
import com.mobile_service_provider.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    private final PackageInfoRepository packageInfoRepository;

    private final UsersPackageCreditService usersPackageCreditService;

    private final ModelMapper modelMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    @CacheEvict(value = "users", allEntries = true)
    public UsersDto createUser(Users newUser) {
        newUser.setCreatedBy("admin");

        newUser.setCreatedDate(new Date());

        newUser.setUserGroup(UserGroup.BRONZE);

        newUser.setStatus("active");

        checkStudent(false, newUser);

        String notificationMessage = "%s your Account has been created successfully.";
        System.out.println("User account for " + newUser.getName() +" has been created");
        String senderMessage = String.format(notificationMessage, newUser.getName(),  newUser.getId() );
        kafkaTemplate.send("create-user",  senderMessage);


        return modelMapper.map(usersRepository.save(newUser), UsersDto.class);
    }

    @Override
    @Cacheable(value = "users")
    public List<UsersDto> getAllUsers() {
        List<Users> users = usersRepository.findAll();

        List<UsersDto> usersDto = users.stream().map( user -> modelMapper.map(user, UsersDto.class)).toList();

        return usersDto;
    }

    @Override
    @Cacheable("users")
    public UsersDto getUser(int id) {
        Optional<Users> target = usersRepository.findById(id);

        if(target.isPresent())
            return modelMapper.map(target.get(), UsersDto.class);

        return null;
    }

    @Override
    @CacheEvict(value = "users", allEntries = true)
    public boolean deleteUser(int id) {

        Optional<Users> target = usersRepository.findById(id);

        if(target.isPresent()){
            target.get().setPackageInfos(null);
            usersRepository.deleteById(id);

            String deleteMessage = "%s your Account has been deleted successfully.";
            System.out.println("User "+target.get().getId()+" was deleted.");
            String senderMessage = String.format(deleteMessage, target.get().getName());
            kafkaTemplate.send("delete-user",  senderMessage);


            return true;
        }

        return false;
    }

    @Override
    public boolean registerPackage(int userId, int packageId) {
        Optional<Users> targetUser = usersRepository.findById(userId);

        Optional<PackageInfo> targetPackage = packageInfoRepository.findById(packageId);

        if(targetUser.isPresent()  && targetPackage.isPresent()){
            targetUser.get().getPackageInfos().add(targetPackage.get());

            targetPackage.get().getUsers().add(targetUser.get());

            usersRepository.save(targetUser.get());

            packageInfoRepository.save(targetPackage.get());

            usersPackageCreditService.fillUserPackageCredits(targetUser.get(), targetPackage.get());

            String registeredMessage = "Dear %s, package %s has been registered to your account.";
            System.out.println("User " + targetUser.get().getId() +" had the following package registered : " + targetPackage.get().getId());
            String senderMessage = String.format(registeredMessage, targetUser.get().getName(), targetPackage.get().getName());
            kafkaTemplate.send("register-package",  senderMessage);


            return true;

        }

        return false;

    }

    @Override
    @Cacheable("userPackage")
    public List<PackageDto> getUserPackages(int id){
        Optional<Users> target = usersRepository.findById(id);

        if(target.isPresent()){
            return target.get().getPackageInfos().stream().map(pack -> modelMapper.map(pack, PackageDto.class)).toList();
        }
        else
            return null;
    }

    @Override
    public boolean checkStudent(boolean isStudent, Users user) {
        if(isStudent){

         user.setStudent(true);

         return true;
        }

        user.setStudent(false);

        return false;
    }

    @Override
    public boolean switchUserGroup(UserGroup userGroup, Users user) {
        if(user.getUserGroup() != userGroup){
            user.setUserGroup(userGroup);

            usersRepository.save(user);

            return true;
        }

        return false;
    }

    @Override
    @CacheEvict(value = "users_credit", allEntries = true)
    public void updateCredits(int userId, CreditType creditType, BigDecimal amount){
        Optional<Users> targetUser = usersRepository.findById(userId);

        targetUser.ifPresentOrElse(users -> usersPackageCreditService.updateUserCredits(users, creditType, amount),
                () -> System.out.println("Could not update credits"));

    }

    @Override
    @Cacheable("users_credit")
    public List<CreditDto> getRemainingCredits(Users user){
        return usersPackageCreditService.getUserCredits(user).stream().map( credit -> modelMapper.map(credit, CreditDto.class)).toList();
    }

    @Override
    public Users getById(int id) {
         Optional<Users> target = usersRepository.findById(id);

        return target.orElse(null);

    }
}
