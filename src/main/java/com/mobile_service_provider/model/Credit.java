
package com.mobile_service_provider.model;
        import jakarta.persistence.*;
        import lombok.Getter;
        import lombok.Setter;

        import java.math.BigDecimal;
        import java.util.List;


@Entity
@Table(name = "credits")
@Setter
@Getter
public class Credit extends BaseEntity{

    @Id
    @Column(name= "credit_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "seqGen", sequenceName = "seq")
    private int id;

    @Enumerated(EnumType.STRING)
    private CreditType creditType;

    @Column(name = "credit_amount")
    private BigDecimal creditAmount;

    @ManyToOne
    @JoinColumn(name="package_id")
    private PackageInfo packageInfo;


    @OneToMany(mappedBy = "credits", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PackageTypeCredit> packageTypeCredits;


    @OneToMany(mappedBy = "credits", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<UsersPackageCredit> usersPackageCredits;



}
