package com.course.controller;

import com.course.entity.Department;
import com.course.exception.DepartmentDeletionException;
import com.course.exception.DepartmentNotFoundException;
import com.course.exception.InvalidJwtAuthenticationException;
import com.course.service.DepartmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentsController {

    private DepartmentsService departmentsService;

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Department> addDepartment(@RequestBody Department department) {
        try {
            return new ResponseEntity<>(departmentsService.addDepartments(department), HttpStatus.CREATED);
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Department> updateDepartment(@RequestBody Department department) {
        try {
            return new ResponseEntity<>(departmentsService.updateDepartments(department), HttpStatus.OK);
        } catch (DepartmentNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Department>> getAllDepartments() {
        try {
            return new ResponseEntity<>(departmentsService.departmentsList(), HttpStatus.OK);
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(departmentsService.findDepartmentsById(id), HttpStatus.OK);
        } catch (DepartmentNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @GetMapping("/getByName/{name}")
    public ResponseEntity<Department> getDepartmentByName(@PathVariable("name") String name) {
        try {
            return new ResponseEntity<>(departmentsService.findDepartmentsByName(name), HttpStatus.OK);
        } catch (DepartmentNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity deleteDepartmentById(@PathVariable("id") Long id) {
        try {
            departmentsService.deleteDepartments(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (DepartmentNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (DepartmentDeletionException ex) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @Autowired
    public void setDepartmentsService(DepartmentsService departmentsService) {
        this.departmentsService = departmentsService;
    }
}
