package com.mobile_service_provider.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@Data
public class Users extends BaseEntity{

    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_seqGen", sequenceName = "user_seq", initialValue = 1)
    private int id;

    @Column(name = "user_name", nullable = false)
    private String name;

    @Column(name = "user_phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "user_email", nullable = false)
    private String email;

    @Column(name = "user_date_of_birth")
    private Date dateOfBirth;

    @Column(name = "user_gender")
    private String gender;

    @Column(name = "user_status")
    private String status;

    @Column(name = "is_student")
    private boolean isStudent;

    @Enumerated(EnumType.STRING)
    private UserGroup userGroup;

    @OneToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private Set<PackageInfo> packageInfos;

    @OneToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private Set<UsersPackageCredit> credits;



}
