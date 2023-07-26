package com.mobile_service_provider.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "package_type")
public class PackageType extends BaseEntity {

    @Id
    @Column(name = "package_type_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "type_seqGen", sequenceName = "type_seq", initialValue = 1)
    private int packageTypeId;

    @Column(name = "package_type_name", nullable = false)
    private String packageTypeName;

    @OneToMany(mappedBy = "packageType", fetch = FetchType.EAGER)
    private Set<PackageInfo> packageInfoSet;




}
