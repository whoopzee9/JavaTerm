package com.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityManagerFactory;

@SpringBootApplication(scanBasePackages = {"com.course.service","com.course.controller", "com.course.repository", "com.course.service.impl",
        "com.course.entity", "com.course.controller", "com.course.security", "com.course.security.jwt", "com.course.config", "com.course"})
//@ComponentScan(basePackages = {"com.course.service","com.course.controller", "com.course.repository", "com.course.service.impl"})
//@EnableJpaRepositories
@EnableJpaRepositories("com.course.repository")
//@EnableAutoConfiguration
public class AccountingAutomation {

    public static void main(String[] args) {
        System.out.println("here1");
        SpringApplication.run(AccountingAutomation.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
