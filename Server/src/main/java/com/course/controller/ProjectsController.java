package com.course.controller;

import com.course.entity.Project;
import com.course.exception.InvalidJwtAuthenticationException;
import com.course.exception.ProjectNotFoundException;
import com.course.service.ProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectsController {

    private ProjectsService projectsService;

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Project> addProject(@RequestBody Project project) {
        try {
            return new ResponseEntity<>(projectsService.addProjects(project), HttpStatus.CREATED);
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Project> updateProject(@RequestBody Project project) {
        try {
            return new ResponseEntity<>(projectsService.updateProjects(project), HttpStatus.OK);
        } catch (ProjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Project>> getAllProjects() {
        try {
            return new ResponseEntity<>(projectsService.projectsList(), HttpStatus.OK);
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(projectsService.findProjectsById(id), HttpStatus.OK);
        } catch (ProjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @GetMapping("/getByName/{name}")
    public ResponseEntity<Project> getProjectByName(@PathVariable("name") String name) {
        try {
            return new ResponseEntity<>(projectsService.findProjectsByName(name), HttpStatus.OK);
        } catch (ProjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @GetMapping("/getByDepartmentId/{id}")
    public ResponseEntity<List<Project>> getByDepartmentId(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(projectsService.findByDepartmentId(id), HttpStatus.OK);
        } catch (ProjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @GetMapping("/getByDepartmentName/{name}")
    public ResponseEntity<List<Project>> getByDepartmentName(@PathVariable("name") String name) {
        try {
            return new ResponseEntity<>(projectsService.findByDepartmentName(name), HttpStatus.OK);
        } catch (ProjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity deleteProjectById(@PathVariable("id") Long id) {
        try {
            projectsService.deleteProjects(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (ProjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @Autowired
    public void setProjectsService(ProjectsService projectsService) {
        this.projectsService = projectsService;
    }
}
