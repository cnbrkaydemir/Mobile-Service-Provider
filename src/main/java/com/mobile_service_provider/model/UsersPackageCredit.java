package com.mobile_service_provider.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user_package_credit")
public class UsersPackageCredit  extends BaseEntity{
    @Id
    @Column(name = "credit_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "utype_seqGen", sequenceName = "utype_seq", initialValue = 1)
    private int creditId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne
    @JoinColumn(name = "package_id")
    private PackageInfo packageInfo;

    @Enumerated(EnumType.STRING)
    private CreditType creditType;

    @Column(name = "credit_amount")
    private BigDecimal creditAmount;



}
