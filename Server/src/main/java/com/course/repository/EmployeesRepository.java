package com.course.repository;

import com.course.entity.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;


public interface EmployeesRepository extends CrudRepository<Employee, Long> {
    @Query("select e from Employee e where e.firstName = :first and e.lastName = :last and " +
            "e.patherName = :pather")
    Collection<Employee> findByName(@Param("first") String first, @Param("last") String last, @Param("pather") String pather);
}
