package com.mobile_service_provider.service.impl;

import com.mobile_service_provider.dto.CreditDto;
import com.mobile_service_provider.model.Credit;
import com.mobile_service_provider.model.CreditType;
import com.mobile_service_provider.model.PackageInfo;
import com.mobile_service_provider.repository.CreditRepository;
import com.mobile_service_provider.service.CreditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CreditServiceImplTest {

    private ModelMapper modelMapper;

    private CreditRepository creditRepository;

    private CreditService creditService;

    @BeforeEach
    void setUp() {
        modelMapper= Mockito.mock(ModelMapper.class);
        creditRepository= Mockito.mock(CreditRepository.class);
        creditService = new CreditServiceImpl(creditRepository, modelMapper);
    }

    @Test
    void getCredit() {
        int id = 42;
        Credit credit = new Credit(CreditType.INTERNET, BigDecimal.valueOf(50), new PackageInfo());

        Mockito.when(creditRepository.findById(id)).thenReturn(Optional.of(credit));
        Mockito.when(modelMapper.map(credit, CreditDto.class)).thenReturn(CreditDto.of(credit));


        assertEquals(modelMapper.map(credit, CreditDto.class), creditService.getCredit(id));

    }

    @Test
    void getCreditWhenNotExists() {
        int id = 312;

        Credit credit = new Credit(CreditType.INTERNET, BigDecimal.valueOf(50), new PackageInfo());

        Mockito.when(creditRepository.findById(id)).thenReturn(Optional.empty());
        Mockito.when(modelMapper.map(credit, CreditDto.class)).thenReturn(CreditDto.of(credit));


        assertNull(creditService.getCredit(id));

    }

    @Test
    void saveCredit() {
        int id= 99;
        Credit credit = new Credit(CreditType.INTERNET, BigDecimal.valueOf(50), new PackageInfo());
        credit.setId(id);

        Mockito.when(creditRepository.save(credit)).thenReturn(credit);
        Mockito.when(creditRepository.findById(id)).thenReturn(Optional.of(credit));

        creditService.saveCredit(credit);

        assertEquals(credit, creditRepository.findById(id).get());

    }

}