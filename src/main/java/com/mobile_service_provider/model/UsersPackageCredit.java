package com.mobile_service_provider.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "user_package_credit")
public class UsersPackageCredit  extends BaseEntity{
    @Id
    @Column(name = "user_credit_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "utype_seqGen", sequenceName = "utype_seq", initialValue = 1)
    private int userPackageId;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    @JoinColumn(name = "package_id")
    private PackageInfo packageInfo;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    @JoinColumn(name = "credit_id")
    private Credit credits;


    @Enumerated(EnumType.STRING)
    private CreditType creditType;

    @Column(name = "credit_amount", nullable = false)
    private BigDecimal creditAmount;




}
