package com.course.service;

import com.course.entity.Projects;

import java.util.List;

public interface ProjectsService {
    List<Projects> projectsList();
    Projects findProjectsById(Long id);
    Projects findProjectsByName(String name);
    void deleteProjects(Long id);
    Projects addProjects(Projects projects);
    Projects updateProjects(Projects projects);
}
