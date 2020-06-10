package com.course.service.impl;

import com.course.exception.ProjectNotFoundException;
import com.course.repository.ProjectsRepository;
import com.course.entity.Project;
import com.course.service.ProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectsServiceImpl implements ProjectsService {

    @Autowired
    private ProjectsRepository projectsRepository;

    @Override
    public List<Project> projectsList() {
        return (List<Project>) projectsRepository.findAll();
    }

    @Override
    public Project findProjectsById(Long id) {
        Optional<Project> projects = projectsRepository.findById(id);

        if (projects.isPresent()) {
            return projects.get();
        } else {
            throw new ProjectNotFoundException("Project not found!");
        }
    }

    @Override
    public Project findProjectsByName(String name) {
        Project project = projectsRepository.findByName(name);

        if (project != null) {
            return project;
        } else {
            throw new ProjectNotFoundException("Project not found!");
        }
    }

    @Override
    public List<Project> findByDepartmentId(Long id) {
        List<Project> list = (List<Project>) projectsRepository.findByDepartmentId(id);
        if (!list.isEmpty()) {
            return list;
        } else {
            throw new ProjectNotFoundException("Project not found!");
        }
    }

    @Override
    public List<Project> findByDepartmentName(String name) {
        List<Project> list = (List<Project>) projectsRepository.findByDepartmentName(name);
        if (!list.isEmpty()) {
            return list;
        } else {
            throw new ProjectNotFoundException("Project not found!");
        }
    }

    @Override
    public void deleteProjects(Long id) {
        try{
            projectsRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ProjectNotFoundException("Project not found!");
        }
    }

    @Override
    public Project addProjects(Project project) {
        return projectsRepository.save(project);
    }

    @Override
    public Project updateProjects(Project project) {
        Optional<Project> tmp = projectsRepository.findById(project.getId());
        if (tmp.isPresent()) {
            return projectsRepository.save(project);
        } else {
            throw new ProjectNotFoundException("Project not found!");
        }
    }
}
