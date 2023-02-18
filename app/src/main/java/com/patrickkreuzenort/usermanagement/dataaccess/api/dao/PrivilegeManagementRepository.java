package com.patrickkreuzenort.usermanagement.dataaccess.api.dao;

import com.patrickkreuzenort.usermanagement.dataaccess.api.PrivilegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeManagementRepository extends JpaRepository<PrivilegeEntity, Long> {

    PrivilegeEntity findByName(String name);
}
