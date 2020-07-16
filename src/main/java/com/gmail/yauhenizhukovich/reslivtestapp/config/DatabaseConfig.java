package com.gmail.yauhenizhukovich.reslivtestapp.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.lang.invoke.MethodHandles;

@Configuration
public class DatabaseConfig {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource);
        entityManager.setPackagesToScan("com.gmail.yauhenizhukovich.reslivtestapp.repository.model");
        entityManager.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        logger.debug("Entity manager bean initialized and ready to use.");
        return entityManager;
    }
}
