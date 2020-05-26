package com.course.repository;

import com.course.entity.Departments;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


public interface DepartmentsRepository extends CrudRepository<Departments, Long> {
    @Query("select d from Departments d where d.name = :name")
    Departments findByName(@Param("name") String name);
}
