package com.mobile_service_provider.service;

import com.mobile_service_provider.dto.CreditDto;
import com.mobile_service_provider.model.Credit;

public interface CreditService {


    boolean deleteCredit(int creditId);

    CreditDto getCredit(int creditId);

    void saveCredit(Credit credit);



}
