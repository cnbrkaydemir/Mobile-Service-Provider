package com.mobile_service_provider.repository;

import com.mobile_service_provider.model.PackageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PackageInfoRepository extends JpaRepository<PackageInfo, Integer> {



}
