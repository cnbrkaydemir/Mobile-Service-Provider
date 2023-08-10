package com.mobile_service_provider.service.impl;

import com.mobile_service_provider.model.TransactionLog;
import com.mobile_service_provider.repository.TransactionLogRepository;
import com.mobile_service_provider.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    private final TransactionLogRepository transactionLogRepository;

    @KafkaListener(topics = {"create-package", "delete-package", "register-package", "delete-user", "create-user","request-response"}, groupId = "group-id")
    public void consume(String message){
        logger.info(String.format("Message receiver \n  %s", message));
    }

    @Override
    @KafkaListener(topics = "transactions")
    public void consumeTransaction(String transaction) {
        TransactionLog transactionLog = new TransactionLog();
        transactionLog.setTransactionMessage(transaction);
        transactionLogRepository.save(transactionLog);


        logger.info(String.format("Transaction message receiver \n  %s", transaction));
    }
}
