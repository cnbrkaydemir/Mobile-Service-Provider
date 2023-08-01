package com.mobile_service_provider.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "transaction_log")
@Setter
@Getter
public class TransactionLog {


    @Id
    @Column(name= "transaction_log_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "seqGen", sequenceName = "seq")
    private int id;


    @Column(name = "transaction_message")
    private String transactionMessage;




}
