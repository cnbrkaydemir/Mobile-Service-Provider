package com.mobile_service_provider.repository;

import com.mobile_service_provider.model.PackageTypeCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageTypeCreditRepository extends JpaRepository<PackageTypeCredit, Integer> {
}
