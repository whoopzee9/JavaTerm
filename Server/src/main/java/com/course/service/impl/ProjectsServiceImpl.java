package com.course.service.impl;

import com.course.exception.ProjectNotFoundException;
import com.course.repository.ProjectsRepository;
import com.course.entity.Projects;
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
    public List<Projects> projectsList() {
        return (List<Projects>) projectsRepository.findAll();
    }

    @Override
    public Projects findProjectsById(Long id) {
        Optional<Projects> projects = projectsRepository.findById(id);

        if (projects.isPresent()) {
            return projects.get();
        } else {
            throw new ProjectNotFoundException("Project not found!");
        }
    }

    @Override
    public Projects findProjectsByName(String name) {
        Projects projects = projectsRepository.findByName(name);

        if (projects != null) {
            return projects;
        } else {
            throw new ProjectNotFoundException("Project not found!");
        }
    }

    @Override
    public List<Projects> findByDepartmentId(Long id) {
        List<Projects> list = (List<Projects>) projectsRepository.findByDepartmentId(id);
        if (!list.isEmpty()) {
            return list;
        } else {
            throw new ProjectNotFoundException("Project not found!");
        }
    }

    @Override
    public List<Projects> findByDepartmentName(String name) {
        List<Projects> list = (List<Projects>) projectsRepository.findByDepartmentName(name);
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
    public Projects addProjects(Projects projects) {
        return projectsRepository.save(projects);
    }

    @Override
    public Projects updateProjects(Projects projects) {
        Optional<Projects> tmp = projectsRepository.findById(projects.getId());
        if (tmp.isPresent()) {
            return projectsRepository.save(projects);
        } else {
            throw new ProjectNotFoundException("Project not found!");
        }
    }
}
