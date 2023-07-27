package com.mobile_service_provider.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "package_info")
@Getter
@Setter
public class PackageInfo extends BaseEntity{

    @Id
    @Column(name= "package_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "seqGen", sequenceName = "seq")
    private int id;

    @Column(name = "package_name", nullable = false)
    private String name;


    @Column(name = "package_duration", nullable = false)
    private byte duration;

    @Column(name = "package_start_date")
    private Date startDate;

    @Column(name = "package_end_date", nullable = false)
    private Date endDate;


    @Column(name = "package_price", nullable = false)
    private BigDecimal price;

    @Column(name = "package_active", nullable = false)
    private boolean isActive;


    @Enumerated(EnumType.STRING)
    private PackageGroup packageGroup;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Users> users;


    @OneToMany(mappedBy = "packageInfo", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Credit> credits;


    @OneToMany(mappedBy = "packageInfo", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PackageTypeCredit> packageTypeCredits;


    @OneToMany(mappedBy = "packageInfo", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<UsersPackageCredit> usersPackageCredits;



}
