package com.mobile_service_provider.repository;

import com.mobile_service_provider.model.UsersPackageCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersPackageCreditRepository  extends JpaRepository<UsersPackageCredit, Integer> {
}
