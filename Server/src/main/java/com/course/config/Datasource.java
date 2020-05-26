package com.course.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
//@EnableJpaRepositories("com.course.repository")
public class Datasource {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/JavaTerm?serverTimezone=UTC&useSSL=false"); //?serverTimezone=UTC&useSSL=false
        dataSource.setUsername("postgres");
        dataSource.setPassword("root");
        return dataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(true);
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setJpaProperties(getHibernateProperties());
        factory.setPackagesToScan("com.course.entity");
        factory.setDataSource(dataSource());
        factory.afterPropertiesSet();
        return factory.getObject();
    }


    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setDataSource(dataSource());
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }

    Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.ddl-auto", "create");
        return properties;
    }
}