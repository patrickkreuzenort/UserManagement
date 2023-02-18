package com.patrickkreuzenort.configuration;

import com.patrickkreuzenort.usermanagement.dataaccess.api.PrivilegeEntity;
import com.patrickkreuzenort.usermanagement.dataaccess.api.RoleEntity;
import com.patrickkreuzenort.usermanagement.dataaccess.api.UserEntity;
import com.patrickkreuzenort.usermanagement.dataaccess.api.dao.PrivilegeManagementRepository;
import com.patrickkreuzenort.usermanagement.dataaccess.api.dao.RoleManagementRepository;
import com.patrickkreuzenort.usermanagement.dataaccess.api.dao.UserManagementRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserManagementRepository userManagementRepository;

    @Autowired
    private RoleManagementRepository roleManagementRepository;

    @Autowired
    private PrivilegeManagementRepository privilegeManagementRepository;


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        PrivilegeEntity readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        PrivilegeEntity writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        List<PrivilegeEntity> adminPrivilegeEntities = Arrays.asList(readPrivilege, writePrivilege);

        createRoleIfNotFound("ROLE_ADMIN", adminPrivilegeEntities);
        createRoleIfNotFound("ROLE_USER", Collections.singletonList(readPrivilege));

        RoleEntity adminRole = roleManagementRepository.findByName("ROLE_ADMIN");
        UserEntity user = new UserEntity();
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setPassword(PasswordEncoder.getEncoder().encode("test"));
        user.setEmail("test@test.com");
        user.setRoles(Arrays.asList(adminRole));
        user.setEnabled(true);
        userManagementRepository.save(user);

        alreadySetup = true;

    }

    @Transactional
    PrivilegeEntity createPrivilegeIfNotFound(String name) {

        PrivilegeEntity privilege = privilegeManagementRepository.findByName(name);
        if (privilege == null) {
            privilege = new PrivilegeEntity(name);
            privilegeManagementRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    RoleEntity createRoleIfNotFound(String name, Collection<PrivilegeEntity> privileges) {

        RoleEntity role = roleManagementRepository.findByName(name);
        if (role == null) {
            role = new RoleEntity(name);
            role.setPrivileges(privileges);
            roleManagementRepository.save(role);
        }
        return role;
    }
}
