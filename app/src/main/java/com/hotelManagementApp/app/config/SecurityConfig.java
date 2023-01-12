package com.hotelManagementApp.app.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    // add a reference to security data source
    @Autowired
    @Qualifier("securityDataSource")
    private DataSource securityDataSource;

    @Bean
    public JdbcUserDetailsManager users() {
        return new JdbcUserDetailsManager(securityDataSource);
    }

}
