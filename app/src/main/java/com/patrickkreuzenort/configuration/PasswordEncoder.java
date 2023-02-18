package com.patrickkreuzenort.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public final class PasswordEncoder {

    @Bean
    public static BCryptPasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

}
