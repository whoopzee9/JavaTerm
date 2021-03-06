package com.course;

import com.course.entity.Employee;
import com.course.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.course.repository.jpa.UserRepository;

@Component
public class TestDataUnit implements CommandLineRunner {

    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        employeesRepository.save(new Employee("Alex", "Bublyaev", "Vyacheslavovich",
                "CEO", (float) 10000));
        employeesRepository.save(new Employee("Alex1", "Bublyaev1", "Vyacheslavovich1",
                "User", (float) 100));

        /*userRepository.save(new User("user", passwordEncoder.encode("pwd"),
                Collections.singletonList("ROLE_USER")));
        userRepository.save(new User("admin", passwordEncoder.encode("apwd"),
                Collections.singletonList("ROLE_ADMIN")));
*/
        System.out.println(userRepository.findAll());
    }
}
