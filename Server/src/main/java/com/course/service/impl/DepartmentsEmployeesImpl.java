package com.course.service.impl;

import com.course.exception.DepartmentsEmployeesNotFoundException;
import com.course.entity.DepartmentsEmployees;
import com.course.exception.EmployeeNotFoundException;
import com.course.service.DepartmentsEmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import com.course.repository.DepartmentsEmployeesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentsEmployeesImpl implements DepartmentsEmployeesService {

    @Autowired
    private DepartmentsEmployeesRepository departmentsEmployeesRepository;

    @Override
    public List<DepartmentsEmployees> departmentsEmployeesList() {
        return (List<DepartmentsEmployees>) departmentsEmployeesRepository.findAll();
    }

    @Override
    public List<DepartmentsEmployees> findByDepartmentId(Long id) {
        List<DepartmentsEmployees> list;
        list = (List<DepartmentsEmployees>) departmentsEmployeesRepository.findByDepartmentId(id);

        if (!list.isEmpty()) {
            return list;
        } else {
            throw new DepartmentsEmployeesNotFoundException("DepartmentsEmployees not found!");
        }
    }

    @Override
    public List<DepartmentsEmployees> findByDepartmentName(String name) {
        List<DepartmentsEmployees> list;
        list = (List<DepartmentsEmployees>) departmentsEmployeesRepository.findByDepartmentName(name);

        if (!list.isEmpty()) {
            return list;
        } else {
            throw new DepartmentsEmployeesNotFoundException("DepartmentsEmployees not found!");
        }
    }

    @Override
    public List<DepartmentsEmployees> findByEmployeeId(Long id) {
        List<DepartmentsEmployees> list;
        list = (List<DepartmentsEmployees>) departmentsEmployeesRepository.findByEmployeeId(id);

        if (!list.isEmpty()) {
            return list;
        } else {
            throw new DepartmentsEmployeesNotFoundException("DepartmentsEmployees not found!");
        }
    }

    @Override
    public List<DepartmentsEmployees> findByEmployeeName(String first, String last, String pather) {
        List<DepartmentsEmployees> list;
        list = (List<DepartmentsEmployees>) departmentsEmployeesRepository.findByEmployeeName(first, last, pather);

        if (!list.isEmpty()) {
            return list;
        } else {
            throw new DepartmentsEmployeesNotFoundException("DepartmentsEmployees not found!");
        }
    }

    @Override
    public void deleteDepartmentEmployee(Long id) {
        try{
            departmentsEmployeesRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new DepartmentsEmployeesNotFoundException("DepartmentsEmployees not found!");
        }
    }

    @Override
    public DepartmentsEmployees addDepartmentsEmployees(DepartmentsEmployees departmentsEmployees) {
        return departmentsEmployeesRepository.save(departmentsEmployees);
    }

    @Override
    public DepartmentsEmployees updateDepartmentsEmployees(DepartmentsEmployees departmentsEmployees) {
        Optional<DepartmentsEmployees> tmp = departmentsEmployeesRepository.findById(departmentsEmployees.getId());
        if (tmp.isPresent()) {
            return departmentsEmployeesRepository.save(departmentsEmployees);
        } else {
            throw new DepartmentsEmployeesNotFoundException("DepartmentsEmployees not found!");
        }
    }
}
