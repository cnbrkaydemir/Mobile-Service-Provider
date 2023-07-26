package com.mobile_service_provider.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "package_type_credit")
public class PackageTypeCredit extends BaseEntity{

    @Id
    @Column(name = "package_credit_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ptype_seqGen", sequenceName = "ptype_seq", initialValue = 1)
    private int creditId;

    @ManyToOne
    @JoinColumn(name = "package_id")
    private PackageInfo packageInfo;

    @Enumerated(EnumType.STRING)
    private CreditType creditType;

    @Column(name = "credit_amount")
    private BigDecimal creditAmount;


}
