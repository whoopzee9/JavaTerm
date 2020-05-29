package com.course.repository;

import com.course.entity.Employees;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;


public interface EmployeesRepository extends CrudRepository<Employees, Long> {
    @Query("select e from Employees e where e.firstName = :first and e.lastName = :last and " +
            "e.patherName = :pather")
    Collection<Employees> findByName(@Param("first") String first, @Param("last") String last, @Param("pather") String pather);
}
