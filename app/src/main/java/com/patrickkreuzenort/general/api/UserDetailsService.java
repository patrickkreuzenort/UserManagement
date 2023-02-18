package com.patrickkreuzenort.general.api;

import com.patrickkreuzenort.general.exceptions.EmailExistsException;
import com.patrickkreuzenort.to.UserEto;
import com.patrickkreuzenort.usermanagement.dataaccess.api.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsService {
    UserDetails loadUserByUsername(String email) throws EntityNotFoundException;

    UserEntity registerNewUserAccount(UserEto userEto) throws EmailExistsException;
}
