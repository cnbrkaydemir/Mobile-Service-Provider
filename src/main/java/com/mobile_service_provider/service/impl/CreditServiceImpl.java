package com.mobile_service_provider.service.impl;

import com.mobile_service_provider.dto.CreditDto;
import com.mobile_service_provider.model.Credit;
import com.mobile_service_provider.repository.CreditRepository;
import com.mobile_service_provider.service.CreditService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {

    private final CreditRepository creditRepository;

    private final ModelMapper modelMapper;



    @Override
    public CreditDto getCredit(int creditId) {
        if(creditRepository.findById(creditId).isPresent()){
            return modelMapper.map(creditRepository.findById(creditId).get(), CreditDto.class);
        }

        return null;
    }

    @Override
    public void saveCredit(Credit credit) {
        if(credit != null){
            creditRepository.save(credit);
        }

    }
}
