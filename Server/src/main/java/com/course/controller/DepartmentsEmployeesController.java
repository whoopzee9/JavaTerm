package com.course.controller;

import com.course.entity.DepartmentsEmployees;
import com.course.exception.DepartmentsEmployeesNotFoundException;
import com.course.exception.InvalidJwtAuthenticationException;
import com.course.service.DepartmentsEmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/departmentsEmployees")
public class DepartmentsEmployeesController {

    private DepartmentsEmployeesService service;

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<DepartmentsEmployees> addDepartmentsEmployees(@RequestBody DepartmentsEmployees DE) {
        try {
            return new ResponseEntity<>(service.addDepartmentsEmployees(DE), HttpStatus.CREATED);
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<DepartmentsEmployees> updateDepartmentsEmployees(@RequestBody DepartmentsEmployees DE) {
        try {
            return new ResponseEntity<>(service.updateDepartmentsEmployees(DE), HttpStatus.OK);
        } catch (DepartmentsEmployeesNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<DepartmentsEmployees>> getAllDepartmentsEmployees() {
        try {
            return new ResponseEntity<>(service.departmentsEmployeesList(), HttpStatus.OK);
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @GetMapping("/getByDepartmentId/{id}")
    public ResponseEntity<List<DepartmentsEmployees>> getByDepartmentId(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(service.findByDepartmentId(id), HttpStatus.OK);
        } catch (DepartmentsEmployeesNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @GetMapping("/getByDepartmentName/{name}")
    public ResponseEntity<List<DepartmentsEmployees>> getByDepartmentName(@PathVariable("name") String name) {
        try {
            return new ResponseEntity<>(service.findByDepartmentName(name), HttpStatus.OK);
        } catch (DepartmentsEmployeesNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @GetMapping("/getByEmployeeId/{id}")
    public ResponseEntity<List<DepartmentsEmployees>> getByEmployeeId(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(service.findByEmployeeId(id), HttpStatus.OK);
        } catch (DepartmentsEmployeesNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @GetMapping("/getByEmployeeName/{last}/{first}/{pather}")
    public ResponseEntity<List<DepartmentsEmployees>> getByEmployeeName(@PathVariable("first") String first,
                                                                        @PathVariable("last") String last,
                                                                        @PathVariable("pather") String pather) {
        try {
            return new ResponseEntity<>(service.findByEmployeeName(first, last, pather), HttpStatus.OK);
        } catch (DepartmentsEmployeesNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Long id) {
        try {
            service.deleteDepartmentEmployee(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (DepartmentsEmployeesNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (InvalidJwtAuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }


    @Autowired
    public void setService(DepartmentsEmployeesService service) {
        this.service = service;
    }
}
