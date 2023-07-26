package com.mobile_service_provider.repository;

import com.mobile_service_provider.model.PackageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageTypeRepository extends JpaRepository<PackageType, Integer> {

}
