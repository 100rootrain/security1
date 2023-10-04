package com.cos.security1.config.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Please explain the class!!
 *
 * @author : kma04
 * @fileName : bCryptConfig
 * @since : 2023-10-04
 */

@Configuration
public class BCryptConfig {
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }
}
