package com.patrickkreuzenort.usermanagement.dataaccess.api.dao;

import com.patrickkreuzenort.usermanagement.dataaccess.api.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserManagementRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);
}
