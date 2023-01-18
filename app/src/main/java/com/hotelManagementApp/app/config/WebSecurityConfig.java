package com.hotelManagementApp.app.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    // add a reference to security data source
    @Autowired
    @Qualifier("securityDataSource")
    private DataSource securityDataSource;

    @Bean
    public JdbcUserDetailsManager users() {
        return new JdbcUserDetailsManager(securityDataSource);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Only Admin can add or delete a room, and update an existing one
        // Only Manager can see reservation directory

        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/rooms/showFormFor*",
                                "/rooms/save",
                                "/rooms/delete").hasRole("ADMIN")
                        .requestMatchers("rooms/reservations/delete",
                                "rooms/reservations/list").hasRole("MANAGER")
                        .requestMatchers("rooms/reservations/save",
                                "rooms/reservations/search*",
                                "rooms/reservations/showFormFor*").hasAnyRole("MANAGER", "EMPLOYEE")
                        .requestMatchers("/", "/home", "/rooms/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/showLogin")
                        .loginProcessingUrl("/authenticateTheUser")
                        .permitAll()
                )
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");

        return http.build();
    }

}
