package com.patrickkreuzenort.usermanagement.dataaccess.api;

import com.patrickkreuzenort.general.entities.UserManagementEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@Entity
public class PrivilegeEntity extends UserManagementEntity {

    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<RoleEntity> roles;

    public PrivilegeEntity(String name) {
        this.name = name;
    }
}

