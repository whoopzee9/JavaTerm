package com.course.service;

import com.course.entity.Project;

import java.util.List;

public interface ProjectsService {
    List<Project> projectsList();
    Project findProjectsById(Long id);
    Project findProjectsByName(String name);
    List<Project> findByDepartmentId(Long id);
    List<Project> findByDepartmentName(String name);
    void deleteProjects(Long id);
    Project addProjects(Project project);
    Project updateProjects(Project project);
}
