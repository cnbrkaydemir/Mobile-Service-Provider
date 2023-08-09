package com.mobile_service_provider.service.impl;

import com.mobile_service_provider.dto.CreditDto;
import com.mobile_service_provider.dto.PackageDto;
import com.mobile_service_provider.model.Credit;
import com.mobile_service_provider.model.CreditType;
import com.mobile_service_provider.model.PackageInfo;
import com.mobile_service_provider.repository.PackageInfoRepository;
import com.mobile_service_provider.service.CreditService;
import com.mobile_service_provider.service.PackageInfoService;
import com.mobile_service_provider.service.PackageTypeCreditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PackageInfoServiceImplTest {

    private PackageInfoRepository packageInfoRepository;

    private CreditService creditService;

    private PackageTypeCreditService packageTypeCreditService;

    private ModelMapper modelMapper;

    private KafkaTemplate<String, String> kafkaTemplate;

    private PackageInfoService packageInfoService;

    @BeforeEach
    void setUp() {
        this.packageInfoRepository = Mockito.mock(PackageInfoRepository.class);
        this.creditService = Mockito.mock(CreditService.class);
        this.packageTypeCreditService = Mockito.mock(PackageTypeCreditService.class);
        this.modelMapper = Mockito.mock(ModelMapper.class);
        this.kafkaTemplate = Mockito.mock(KafkaTemplate.class);
        this.packageInfoService = new PackageInfoServiceImpl(packageInfoRepository, creditService, packageTypeCreditService, modelMapper, kafkaTemplate);

    }

    @Test
    void createPackage() {
        PackageInfo pack = new PackageInfo("Super Mega 50 GB", Byte.parseByte("30"), new Date(2023, Calendar.JULY,17), new Date(2023, Calendar.AUGUST, 17), BigDecimal.valueOf(129.99), true);
        pack.setUsers(new ArrayList<>());
        pack.setId(127);

        Mockito.when(packageInfoRepository.save(pack)).thenReturn(pack);
        Mockito.when(modelMapper.map(pack, PackageDto.class)).thenReturn(PackageDto.of(pack));


        assertEquals(PackageDto.of(pack), packageInfoService.createPackage(pack));

    }

    @Test
    void getAllPackages() {
        List<PackageInfo> packages = List.of(
                new PackageInfo("Super Mega 50 GB", Byte.parseByte("30"), new Date(2023, Calendar.JULY,17), new Date(2023, Calendar.AUGUST, 17), BigDecimal.valueOf(129.99), true),
                new PackageInfo("Mega Student 30 GB", Byte.parseByte("30"), new Date(2023, Calendar.JULY,17), new Date(2023, Calendar.AUGUST, 17), BigDecimal.valueOf(3999.99), true),
                new PackageInfo("Super Family 60 GB", Byte.parseByte("60"), new Date(2023, Calendar.JULY,17), new Date(2023, Calendar.AUGUST, 17), BigDecimal.valueOf(299.99), true),
                new PackageInfo("Prime Plus", Byte.parseByte("30"), new Date(2023, Calendar.JULY,17), new Date(2023, Calendar.AUGUST, 17), BigDecimal.valueOf(1999.99), true),
                new PackageInfo("Platinum Plus", Byte.parseByte("30"), new Date(2023, Calendar.JULY,17), new Date(2023, Calendar.AUGUST, 17), BigDecimal.valueOf(69.99), true),
                new PackageInfo("Extra Promotion", Byte.parseByte("30"), new Date(2023, Calendar.JULY,17), new Date(2023, Calendar.AUGUST, 17), BigDecimal.valueOf(109.99), true),
                new PackageInfo("20 GB Only", Byte.parseByte("30"), new Date(2023, Calendar.JULY,17), new Date(2023, Calendar.AUGUST, 17), BigDecimal.valueOf(99.99), true));

        Mockito.when(packageInfoRepository.findAll()).thenReturn(packages);
        Mockito.when(modelMapper.map(packages.get(0), PackageDto.class)).thenReturn(PackageDto.of(packages.get(0)));

        List<PackageDto> packageDtos = packages.stream().map( packageInfo -> modelMapper.map(packageInfo, PackageDto.class)).toList();

        assertEquals(packageDtos, packageInfoService.getAllPackages());

    }

    @Test
    void getAllPackagesWhenPackagesEmpty() {

        List<PackageInfo> packages = new ArrayList<>();

       PackageInfo pack = new PackageInfo("Super Mega 50 GB", Byte.parseByte("30"), new Date(2023, Calendar.JULY,17), new Date(2023, Calendar.AUGUST, 17), BigDecimal.valueOf(129.99), true);


        Mockito.when(packageInfoRepository.findAll()).thenReturn(packages);
        Mockito.when(modelMapper.map(pack, PackageDto.class)).thenReturn(PackageDto.of(pack));

        List<PackageDto> retrievedPacks = packageInfoService.getAllPackages();

        assertEquals(0, retrievedPacks.size());
        assertNotNull(retrievedPacks);


    }


    @Test
    void getPackageWhenExists() {
        int id = 23;
        PackageInfo pack = new PackageInfo("Super Mega 50 GB", Byte.parseByte("30"), new Date(2023, Calendar.JULY,17), new Date(2023, Calendar.AUGUST, 17), BigDecimal.valueOf(129.99), true);
        pack.setId(id);

        Mockito.when(packageInfoRepository.findById(id)).thenReturn(Optional.of(pack));
        Mockito.when(modelMapper.map(pack, PackageDto.class)).thenReturn(PackageDto.of(pack));

        PackageDto retrieved = packageInfoService.getPackage(id);

        assertEquals(modelMapper.map(pack, PackageDto.class), retrieved );
        assertEquals(id,retrieved.getId());

    }


    @Test
    void getPackageWhenNotExists() {
        int id = 43;

        PackageInfo pack =  new PackageInfo("Extra Promotion", Byte.parseByte("30"), new Date(2023, Calendar.JULY,17), new Date(2023, Calendar.AUGUST, 17), BigDecimal.valueOf(109.99), true);

        pack.setId(id);

        Mockito.when(packageInfoRepository.findById(id)).thenReturn(Optional.empty());
        Mockito.when(modelMapper.map(pack, PackageDto.class)).thenReturn(PackageDto.of(pack));

        PackageDto retrieved = packageInfoService.getPackage(id);

        assertNull(retrieved);

    }

    @Test
    void fillCredits() {
        int id = 43;

        PackageInfo pack =  new PackageInfo("Prime Plus", Byte.parseByte("30"), new Date(2023, Calendar.JULY,17), new Date(2023, Calendar.AUGUST, 17), BigDecimal.valueOf(1999.99), true);

        pack.setId(id);
        pack.setCredits(new ArrayList<>());

        List<Credit> credits = List.of(
          new Credit(CreditType.SMS, BigDecimal.valueOf(1000), pack),
          new Credit(CreditType.INTERNET, BigDecimal.valueOf(50), pack),
          new Credit(CreditType.CALL, BigDecimal.valueOf(1200), pack)
        );

        Mockito.when(packageInfoRepository.findById(id)).thenReturn(Optional.of(pack));

        assertTrue(packageInfoService.fillCredits(id, credits));
        assertEquals(pack.getCredits(), credits);


    }

    @Test
    void getPackageCredit() {

        int id = 23;

        PackageInfo pack = new PackageInfo("20 GB Only", Byte.parseByte("30"), new Date(2023, Calendar.JULY,17), new Date(2023, Calendar.AUGUST, 17), BigDecimal.valueOf(99.99), true);

        pack.setId(id);
        pack.setCredits(new ArrayList<>());

        List<Credit> credits = List.of(
                new Credit(CreditType.SMS, BigDecimal.valueOf(1000), pack),
                new Credit(CreditType.INTERNET, BigDecimal.valueOf(50), pack),
                new Credit(CreditType.CALL, BigDecimal.valueOf(1200), pack)
        );

        Mockito.when(packageInfoRepository.findById(id)).thenReturn(Optional.of(pack));
        Mockito.when(modelMapper.map(credits.get(0), CreditDto.class)).thenReturn(CreditDto.of(credits.get(0)));
        packageInfoService.fillCredits(id, credits);

        List<CreditDto> expected = credits.stream().map( credit -> modelMapper.map(credit, CreditDto.class)).toList();

        assertEquals(expected, packageInfoService.getPackageCredit(id));

    }

    @Test
    void getPackageCreditWhenCreditsEmpty() {

        int id = 23;

        PackageInfo pack = new PackageInfo("Super Mega 50 GB", Byte.parseByte("30"), new Date(2023, Calendar.JULY,17), new Date(2023, Calendar.AUGUST, 17), BigDecimal.valueOf(129.99), true);
        pack.setId(id);
        pack.setCredits(new ArrayList<>());



        Mockito.when(packageInfoRepository.findById(id)).thenReturn(Optional.of(pack));


        assertEquals(new ArrayList<>(), packageInfoService.getPackageCredit(id));

    }



}