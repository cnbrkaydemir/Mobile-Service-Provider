package com.mobile_service_provider.service.impl;

import com.mobile_service_provider.dto.CreditDto;
import com.mobile_service_provider.dto.PackageDto;
import com.mobile_service_provider.dto.UserPackageDto;
import com.mobile_service_provider.dto.UsersDto;
import com.mobile_service_provider.model.*;
import com.mobile_service_provider.repository.PackageInfoRepository;
import com.mobile_service_provider.repository.UsersRepository;
import com.mobile_service_provider.service.UsersPackageCreditService;
import com.mobile_service_provider.service.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class UsersServiceImplTest {

    private UsersService usersService;

    private  UsersRepository usersRepository;

    private  PackageInfoRepository packageInfoRepository;

    private  UsersPackageCreditService usersPackageCreditService;

    private  ModelMapper modelMapper;


    private  PasswordEncoder passwordEncoder;

    private KafkaTemplate<String, String> kafkaTemplate;




    @BeforeEach
    void setUp() {
        this.usersRepository = Mockito.mock(UsersRepository.class);
        this.packageInfoRepository = Mockito.mock(PackageInfoRepository.class);
        this.usersPackageCreditService = Mockito.mock(UsersPackageCreditService.class);
        this.modelMapper = Mockito.mock(ModelMapper.class);
        this.passwordEncoder = Mockito.mock(PasswordEncoder.class);
        this.kafkaTemplate = Mockito.mock(KafkaTemplate.class);

        this.usersService = new UsersServiceImpl(usersRepository, packageInfoRepository, usersPackageCreditService, modelMapper, passwordEncoder, kafkaTemplate);

    }

    @Test
    void createUser() {

        Users newUser = new Users("John Doe", "42567789543", "john.doe@tt.com", "123456");

        Mockito.when(usersRepository.save(newUser)).thenReturn(newUser);

        Mockito.when(modelMapper.map( usersRepository.save(newUser), UsersDto.class)).thenReturn(UsersDto.of(newUser));

        UsersDto serviceUser = usersService.createUser(newUser);

        UsersDto testUser = UsersDto.of(newUser);

        assertEquals(serviceUser, testUser);

    }

    @Test
    void createAdmin() {
        Users adminUser = new Users("Jane Doe", "45567789543", "jane.doe@tt.com", "password");

        usersService.createAdmin(adminUser);

        Authority adminAuth = adminUser.getAuthorities().iterator().next();

        assertEquals(adminAuth.getName(), "ADMIN");
    }

    @Test
    void getAllUsers() {

        List<Users> allUsers = List.of(
        new Users("Jane Doe", "41567789543", "jane.doe@tt.com", "password"),
        new Users("John Doe", "42567789543", "john.doe@tt.com", "passwor"),
        new Users("Joe Doe", "43567789543", "joe.doe@tt.com", "passwo"),
        new Users("Jack Doe", "44567789543", "jack.doe@tt.com", "passw"),
        new Users("Jones Doe", "45567789543", "jones.doe@tt.com", "pass"));


        Users userN = new Users("Jones Doe", "45567789543", "jones.doe@tt.com", "pass");

        Mockito.when(usersRepository.findAll()).thenReturn(allUsers);
        Mockito.when(modelMapper.map(userN, UsersDto.class)).thenReturn(UsersDto.of(userN));


        List<UsersDto> testAll = allUsers.stream().map(user -> modelMapper.map(user, UsersDto.class)).toList();

        List<UsersDto> getAll = usersService.getAllUsers();

        assertEquals(testAll, getAll);


    }


    @Test
    void getAllUsersWhenEmptyList() {

        List<Users> allUsers = new ArrayList<>();

        Users userN = new Users("Jones Doe", "45567789543", "jones.doe@tt.com", "pass");

        Mockito.when(usersRepository.findAll()).thenReturn(allUsers);
        Mockito.when(modelMapper.map(userN, UsersDto.class)).thenReturn(UsersDto.of(userN));



        List<UsersDto> getAll = usersService.getAllUsers();

        assertEquals(getAll.size(), 0);
        assertNotNull(getAll);


    }


    @Test
    void getUserWhenIdExists() {

        int id = 4;

        Users newUser = new Users("John Doe", "42567789543", "john.doe@tt.com", "123456");
        newUser.setId(id);

        Mockito.when(usersRepository.findById(id)).thenReturn(Optional.of(newUser));
        Mockito.when(modelMapper.map(newUser, UsersDto.class)).thenReturn(UsersDto.of(newUser));


        UsersDto test = modelMapper.map(newUser, UsersDto.class);

        UsersDto getUser = usersService.getUser(id);

        assertEquals(test, getUser);

    }

    @Test
    void getUserWhenIdNotExists() {

        int id = 16;

        Users newUser = new Users("John Doe", "42567789543", "john.doe@tt.com", "123456");


        Mockito.when(usersRepository.findById(id)).thenReturn(Optional.empty());
        Mockito.when(modelMapper.map(Users.class, UsersDto.class)).thenReturn(UsersDto.of(newUser));


        UsersDto getUser = usersService.getUser(id);

        assertNull(getUser);

    }



    @Test
    void registerPackage() {
        Users dummyUser = new Users("Canberk Aydemir", "8765473452", "cano@tt.com", "hidden");
        dummyUser.setPackageInfos( new ArrayList<>());
        dummyUser.setId(127);
        PackageInfo pack = new PackageInfo("Super Mega 50 GB", Byte.parseByte("30"), new Date(2023, Calendar.JULY,17), new Date(2023, Calendar.AUGUST, 17), BigDecimal.valueOf(129.99), true);
        pack.setUsers(new ArrayList<>());
        pack.setId(127);


        Mockito.when(usersRepository.findById(127)).thenReturn(Optional.of(dummyUser));
        Mockito.when(packageInfoRepository.findById(127)).thenReturn(Optional.of(pack));




        assertTrue(usersService.registerPackage(127, 127));
        assertEquals(dummyUser.getPackageInfos().get(0), pack);
        assertEquals(pack.getUsers().get(0), dummyUser);

    }

    @Test
    void getUserPackages() {
        Users dummyUser = new Users("Canberk Aydemir", "8765473452", "cano@tt.com", "hidden");
        dummyUser.setPackageInfos( new ArrayList<>());
        dummyUser.setId(118);
        PackageInfo pack = new PackageInfo("Super Mega 50 GB", Byte.parseByte("30"), new Date(2023, Calendar.JULY,17), new Date(2023, Calendar.AUGUST, 17), BigDecimal.valueOf(129.99), true);
        pack.setUsers(new ArrayList<>());
        pack.setId(118);
        pack.setPackageGroup(PackageGroup.STUDENT);



        Mockito.when(usersRepository.findById(118)).thenReturn(Optional.of(dummyUser));
        Mockito.when(packageInfoRepository.findById(118)).thenReturn(Optional.of(pack));
        Mockito.when(modelMapper.map(pack, PackageDto.class)).thenReturn(PackageDto.of(pack));

        usersService.registerPackage(118, 118);

        assertNotNull(modelMapper.map(pack,PackageDto.class));
        assertEquals(usersService.getUserPackages(118).get(0), modelMapper.map(pack,PackageDto.class));

    }


    @Test
    void getRemainingCredits() {

        Users dummyUser = new Users("Jane Doe", "4265473452", "jane@tt.com", "hellooo");
        dummyUser.setPackageInfos( new ArrayList<>());
        dummyUser.setId(118);
        PackageInfo pack = new PackageInfo("Super Mega 50 GB", Byte.parseByte("30"), new Date(2023, Calendar.JULY,17), new Date(2023, Calendar.AUGUST, 17), BigDecimal.valueOf(129.99), true);
        pack.setUsers(new ArrayList<>());
        pack.setId(118);
        pack.setPackageGroup(PackageGroup.STUDENT);
        pack.setCredits(
                List.of(new Credit(CreditType.SMS, BigDecimal.valueOf(100), pack),
                        new Credit(CreditType.INTERNET, BigDecimal.valueOf(50),pack),
                        new Credit(CreditType.CALL, BigDecimal.valueOf(1000),pack)));

        Credit emptyCredit = new Credit();
        UserPackageDto userPackageDto = new UserPackageDto(118,118);


        Mockito.when(usersRepository.findById(118)).thenReturn(Optional.of(dummyUser));
        Mockito.when(packageInfoRepository.findById(118)).thenReturn(Optional.of(pack));
        Mockito.when(modelMapper.map(pack, PackageDto.class)).thenReturn(PackageDto.of(pack));
        Mockito.when(modelMapper.map(emptyCredit, CreditDto.class)).thenReturn(CreditDto.of(emptyCredit));
        Mockito.when(usersPackageCreditService.getUserCredits(dummyUser, pack)).thenReturn(pack.getUsersPackageCredits());

        usersService.registerPackage(118, 118);

        assertEquals(usersService.getRemainingCredits(userPackageDto).size(), 3 );


    }


}