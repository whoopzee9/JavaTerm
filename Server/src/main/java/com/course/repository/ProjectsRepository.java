package com.course.repository;

import com.course.entity.Projects;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

public interface ProjectsRepository extends CrudRepository<Projects, Long> {
    @Query("select p from Projects p where p.name = :name")
    Projects findByName(@Param("name") String name);

    @Query("select p from Projects p where p.departments.id = :id")
    Collection<Projects> findByDepartmentId(@Param("id") Long id);

    @Query("select p from Projects p where p.departments.name = :name")
    Collection<Projects> findByDepartmentName(@Param("name") String name);
}
