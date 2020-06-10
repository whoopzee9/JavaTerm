package com.course.repository;

import com.course.entity.DepartmentEmployee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface DepartmentsEmployeesRepository extends CrudRepository<DepartmentEmployee, Long> {
    @Query("select de from DepartmentEmployee de where de.departments.name = :name")
    Collection<DepartmentEmployee> findByDepartmentName(@Param("name") String name);

    @Query("select de from DepartmentEmployee de where de.departments.id = :id")
    Collection<DepartmentEmployee> findByDepartmentId(@Param("id") Long id);

    @Query("select de from DepartmentEmployee de where de.employees.id = :id")
    Collection<DepartmentEmployee> findByEmployeeId(@Param("id") Long id);

    @Query("select de from DepartmentEmployee de inner join Employee e on " +
            "e.id = de.employees.id where e.firstName = :first and e.lastName = :last and " +
            "e.patherName = :pather")
    Collection<DepartmentEmployee> findByEmployeeName(@Param("first") String first, @Param("last") String last, @Param("pather") String pather);
}
