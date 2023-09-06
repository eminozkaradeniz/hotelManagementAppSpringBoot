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

/**
 * Configuration class for defining web security settings using Spring Security<
 * This class configures security-related behavior for various HTTP requests and user authentication.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    /**
     * Reference to the security data source used for user authentication and authorization.
     * This data source is used to manage user details and roles for security purposes.
     *
     * @see javax.sql.DataSource
     */
    @Autowired
    @Qualifier("securityDataSource")
    private DataSource securityDataSource;

    /**
     * Defines a bean for managing user details using a JDBC-backed implementation.
     *
     * @return The configured 'users' bean, which manages user details.
     */
    @Bean
    public JdbcUserDetailsManager users() {
        return new JdbcUserDetailsManager(securityDataSource);
    }

    /**
     * Configures the security filter chain for handling HTTP requests.
     *
     * @param http the HttpSecurity object used to configure security filter and request authorizations.
     * @return the configured security filter chain.
     * @throws Exception If an exception occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/rooms/showFormFor*",
                                "/rooms/save",
                                "/rooms/delete").hasRole("ADMIN")
                        .requestMatchers("/rooms/reservations/delete",
                                "/rooms/reservations/showFormForUpdate").hasRole("MANAGER")
                        .requestMatchers("/rooms/reservations/save",
                                "/rooms/reservations/search*",
                                "/rooms/reservations/showFormForAdd*",
                                "/rooms/reservations/list").hasAnyRole("MANAGER", "EMPLOYEE")
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/showLogin")
                        .loginProcessingUrl("/authenticateTheUser")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");

        return http.build();
    }

}
