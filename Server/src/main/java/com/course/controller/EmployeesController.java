package com.course.controller;

import com.course.entity.Employees;
import com.course.exception.EmployeeDeletionException;
import com.course.exception.EmployeeNotFoundException;
import com.course.exception.InvalidJwtAuthenticationException;
import com.course.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    private EmployeesService employeesService;

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Employees> addEmployee(@RequestBody Employees employees) {
        try {
            return new ResponseEntity<>(employeesService.addEmployees(employees), HttpStatus.CREATED);
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Employees> updateEmployee(@RequestBody Employees employees) {
        try {
            return new ResponseEntity<>(employeesService.updateEmployees(employees), HttpStatus.OK);
        } catch (EmployeeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Employees>> getAllEmployees() {
        try {
            return new ResponseEntity<>(employeesService.employeesList(), HttpStatus.OK);
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Employees> getEmployeeById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(employeesService.findEmployeesById(id), HttpStatus.OK);
        } catch (EmployeeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @GetMapping("/getByName/{last}/{first}/{pather}")
    public ResponseEntity<List<Employees>> getEmployeeByName(@PathVariable("first") String first,
                                                             @PathVariable("last") String last,
                                                             @PathVariable("pather") String pather) {
        try {
            return new ResponseEntity<>(employeesService.findEmployeesByName(first, last, pather), HttpStatus.OK);
        } catch (EmployeeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity deleteEmployeeById(@PathVariable("id") Long id) {
        try {
            employeesService.deleteEmployeesById(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (EmployeeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (EmployeeDeletionException ex) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @Autowired
    public void setEmployeesService(EmployeesService employeesService) {
        this.employeesService = employeesService;
    }
}
