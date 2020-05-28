package com.course.controller;

import com.course.entity.Projects;
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
    public ResponseEntity<Projects> addProject(@RequestBody Projects projects) {
        try {
            return new ResponseEntity<>(projectsService.addProjects(projects), HttpStatus.CREATED);
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Projects> updateProject(@RequestBody Projects projects) {
        try {
            return new ResponseEntity<>(projectsService.updateProjects(projects), HttpStatus.OK);
        } catch (ProjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Projects>> getAllProjects() {
        try {
            return new ResponseEntity<>(projectsService.projectsList(), HttpStatus.OK);
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Projects> getProjectById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(projectsService.findProjectsById(id), HttpStatus.OK);
        } catch (ProjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @GetMapping("/getByName/{name}")
    public ResponseEntity<Projects> getProjectByName(@PathVariable("name") String name) {
        try {
            return new ResponseEntity<>(projectsService.findProjectsByName(name), HttpStatus.OK);
        } catch (ProjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @GetMapping("/getByDepartmentId/{id}")
    public ResponseEntity<List<Projects>> getByDepartmentId(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(projectsService.findByDepartmentId(id), HttpStatus.OK);
        } catch (ProjectNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @GetMapping("/getByDepartmentName/{name}")
    public ResponseEntity<List<Projects>> getByDepartmentName(@PathVariable("name") String name) {
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

    @DeleteMapping("/deleteByName/{name}")
    public ResponseEntity deleteProjectByName(@PathVariable("name") String name) {
        try {
            projectsService.deleteProjects(projectsService.findProjectsByName(name).getId());
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
