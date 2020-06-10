package com.course.repository;

import com.course.entity.Department;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface DepartmentsRepository extends CrudRepository<Department, Long> {
    @Query("select d from Department d where d.name = :name")
    Department findByName(@Param("name") String name);
}
