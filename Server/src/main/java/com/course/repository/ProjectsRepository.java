package com.course.repository;

import com.course.entity.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface ProjectsRepository extends CrudRepository<Project, Long> {
    @Query("select p from Project p where p.name = :name")
    Project findByName(@Param("name") String name);

    @Query("select p from Project p where p.departments.id = :id")
    Collection<Project> findByDepartmentId(@Param("id") Long id);

    @Query("select p from Project p where p.departments.name = :name")
    Collection<Project> findByDepartmentName(@Param("name") String name);
}
