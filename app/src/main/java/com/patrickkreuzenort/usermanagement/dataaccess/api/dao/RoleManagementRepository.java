package com.patrickkreuzenort.usermanagement.dataaccess.api.dao;

import com.patrickkreuzenort.usermanagement.dataaccess.api.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleManagementRepository extends JpaRepository<RoleEntity, Long> {

    RoleEntity findByName(String name);
}
