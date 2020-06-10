package com.course.service.impl;

import com.course.exception.DepartmentsEmployeesNotFoundException;
import com.course.entity.DepartmentEmployee;
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
    public List<DepartmentEmployee> departmentsEmployeesList() {
        return (List<DepartmentEmployee>) departmentsEmployeesRepository.findAll();
    }

    @Override
    public List<DepartmentEmployee> findByDepartmentId(Long id) {
        List<DepartmentEmployee> list;
        list = (List<DepartmentEmployee>) departmentsEmployeesRepository.findByDepartmentId(id);

        if (!list.isEmpty()) {
            return list;
        } else {
            throw new DepartmentsEmployeesNotFoundException("DepartmentsEmployees not found!");
        }
    }

    @Override
    public List<DepartmentEmployee> findByDepartmentName(String name) {
        List<DepartmentEmployee> list;
        list = (List<DepartmentEmployee>) departmentsEmployeesRepository.findByDepartmentName(name);

        if (!list.isEmpty()) {
            return list;
        } else {
            throw new DepartmentsEmployeesNotFoundException("DepartmentsEmployees not found!");
        }
    }

    @Override
    public List<DepartmentEmployee> findByEmployeeId(Long id) {
        List<DepartmentEmployee> list;
        list = (List<DepartmentEmployee>) departmentsEmployeesRepository.findByEmployeeId(id);

        if (!list.isEmpty()) {
            return list;
        } else {
            throw new DepartmentsEmployeesNotFoundException("DepartmentsEmployees not found!");
        }
    }

    @Override
    public List<DepartmentEmployee> findByEmployeeName(String first, String last, String pather) {
        List<DepartmentEmployee> list;
        list = (List<DepartmentEmployee>) departmentsEmployeesRepository.findByEmployeeName(first, last, pather);

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
    public DepartmentEmployee addDepartmentsEmployees(DepartmentEmployee departmentEmployee) {
        return departmentsEmployeesRepository.save(departmentEmployee);
    }

    @Override
    public DepartmentEmployee updateDepartmentsEmployees(DepartmentEmployee departmentEmployee) {
        Optional<DepartmentEmployee> tmp = departmentsEmployeesRepository.findById(departmentEmployee.getId());
        if (tmp.isPresent()) {
            return departmentsEmployeesRepository.save(departmentEmployee);
        } else {
            throw new DepartmentsEmployeesNotFoundException("DepartmentsEmployees not found!");
        }
    }
}
