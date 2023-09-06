package com.hotelManagementApp.app.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Configuration class for defining and configuring data sources in the Spring application context.
 * Configuration properties for the data sources are specified in the application's configuration file (application.properties).
 */
@Configuration
public class DataSourceConfig {

    /**
     * Primary data source bean for the application.
     * This data source is marked as primary, making it default data source.
     *
     * @return The configured 'appDataSource' bean.
     */
    @Primary
    @Bean
    @ConfigurationProperties(prefix = "app.datasource")
    public DataSource appDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * Secondary data source bean for security-related data.
     * This data source can be used for security-related database operations.
     *
     * @return The configured 'securityDatasource' bean.
     */
    @Bean
    @ConfigurationProperties(prefix = "security.datasource")
    public DataSource securityDataSource() {
        return DataSourceBuilder.create().build();
    }
    
}
