package com.patrickkreuzenort.general.impl;

import com.patrickkreuzenort.configuration.PasswordEncoder;
import com.patrickkreuzenort.general.api.UserDetailsService;
import com.patrickkreuzenort.general.exceptions.EmailExistsException;
import com.patrickkreuzenort.to.UserEto;
import com.patrickkreuzenort.usermanagement.dataaccess.api.PrivilegeEntity;
import com.patrickkreuzenort.usermanagement.dataaccess.api.RoleEntity;
import com.patrickkreuzenort.usermanagement.dataaccess.api.UserEntity;
import com.patrickkreuzenort.usermanagement.dataaccess.api.dao.RoleManagementRepository;
import com.patrickkreuzenort.usermanagement.dataaccess.api.dao.UserManagementRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("userDetailsService")
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserManagementRepository userManagementRepository;

    @Autowired
    private MessageSource messages;

    @Autowired
    private RoleManagementRepository roleManagementRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws EntityNotFoundException {

        UserEntity userEntity = userManagementRepository.findByEmail(email);

        if (userEntity == null) {
            return new org.springframework.security.core.userdetails.User(
                    " ", " ", true, true, true, true,
                    getAuthorities(Collections.singletonList(roleManagementRepository.findByName("ROLE_USER"))));
        }

        return new org.springframework.security.core.userdetails.User(
                userEntity.getEmail(), userEntity.getPassword(), userEntity.isEnabled(), true, true,
                true, getAuthorities(userEntity.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<RoleEntity> roles) {

        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<RoleEntity> roles) {

        List<String> privileges = new ArrayList<>();
        List<PrivilegeEntity> collection = new ArrayList<>();
        for (RoleEntity role : roles) {
            privileges.add(role.getName());
            collection.addAll(role.getPrivileges());
        }
        for (PrivilegeEntity item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    @Override
    public UserEntity registerNewUserAccount(UserEto userEto) throws EmailExistsException {

        if (emailExist(userEto.getEmail())) {
            throw new EmailExistsException("There is an account with that email adress: " + userEto.getEmail());
        }
        UserEntity user = new UserEntity();

        user.setFirstName(userEto.getFirstName());
        user.setLastName(userEto.getLastName());
        user.setPassword(PasswordEncoder.getEncoder().encode(userEto.getPassword()));
        user.setEmail(userEto.getEmail());

        user.setRoles(Collections.singletonList(roleManagementRepository.findByName("ROLE_USER")));
        return userManagementRepository.save(user);
    }

    private boolean emailExist(String email) {
        return userManagementRepository.findByEmail(email) != null;
    }

}
