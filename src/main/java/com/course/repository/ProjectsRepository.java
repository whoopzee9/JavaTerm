package com.course.repository;

import com.course.entity.Projects;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProjectsRepository extends CrudRepository<Projects, Long> {
    @Query("select p from Projects p where p.name = :name")
    Projects findByName(@Param("name") String name);
}
