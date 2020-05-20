package com.course;

import com.course.entity.Employees;
import com.course.repository.EmployeesRepository;
import com.course.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.course.repository.UserRepository;

import java.util.Collections;

@Component
public class TestDataInit implements CommandLineRunner {

    @Autowired
    EmployeesRepository employeesRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        employeesRepository.save(new Employees("Alex", "Bublyaev", "Vyacheslavovich",
                "CEO", (float) 10000));
        employeesRepository.save(new Employees("Alex1", "Bublyaev1", "Vyacheslavovich1",
                "User", (float) 100));

        userRepository.save(new User("user", passwordEncoder.encode("pwd"),
                Collections.singletonList("ROLE_USER")));
        userRepository.save(new User("admin", passwordEncoder.encode("apwd"),
                Collections.singletonList("ROLE_ADMIN")));
    }
}
