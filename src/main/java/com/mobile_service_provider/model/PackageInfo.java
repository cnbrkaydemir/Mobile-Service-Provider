package com.mobile_service_provider.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "package_info")
@Data
public class PackageInfo extends BaseEntity{

    @Id
    @Column(name= "package_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "seqGen", sequenceName = "seq", initialValue = 1)
    private int id;

    @Column(name = "package_duration", nullable = false)
    private byte duration;

    @Column(name = "package_start_date", nullable = false)
    private Date startDate;

    @Column(name = "package_end_date", nullable = false)
    private Date endDate;


    @Column(name = "package_price", nullable = false)
    private BigDecimal price;

    @Column(name = "package_active", nullable = false)
    private boolean isActive;


    @Enumerated(EnumType.STRING)
    private PackageGroup packageGroup;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;


    @ManyToOne
    @JoinColumn(name = "package_type_id")
    private PackageType packageType;


    @OneToMany(mappedBy = "packageInfo", fetch = FetchType.EAGER)
    private Set<PackageTypeCredit> packageTypeCredits;


    @OneToMany(mappedBy = "packageInfo", fetch = FetchType.EAGER)
    private Set<UsersPackageCredit> usersPackageCredits;



}
