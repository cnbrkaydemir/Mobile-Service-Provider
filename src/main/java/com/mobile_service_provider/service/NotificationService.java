package com.mobile_service_provider.service;

public interface NotificationService {

    public void consume(String message);

    public void consumeTransaction(String transaction);


}
