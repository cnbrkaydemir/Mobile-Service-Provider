package com.mobile_service_provider.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;



@Entity
@Table(name = "package_info")
@Getter
@Setter
@NoArgsConstructor
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


    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Users> users;

    @OneToMany(mappedBy = "packageInfo", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Credit> credits;


    @OneToMany(mappedBy = "packageInfo", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PackageTypeCredit> packageTypeCredits;

    @OneToMany(mappedBy = "packageInfo", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UsersPackageCredit> usersPackageCredits;


    public PackageInfo(String name, byte duration, Date startDate, Date endDate, BigDecimal price, boolean isActive) {
        this.name = name;
        this.duration = duration;
        this.endDate = endDate;
        this.price = price;
        this.isActive = isActive;
    }

}
