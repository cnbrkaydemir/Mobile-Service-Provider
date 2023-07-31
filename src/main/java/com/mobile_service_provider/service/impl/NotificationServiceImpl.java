package com.mobile_service_provider.service.impl;

import com.mobile_service_provider.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @KafkaListener(topics = {"create-package", "delete-package", "register-package", "delete-user", "create-user"}, groupId = "group-id")
    public void consume(String message){
        logger.info(String.format("Message receiver \n  %s", message));
    }

}
