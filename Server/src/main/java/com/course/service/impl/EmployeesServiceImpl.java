package com.course.service.impl;

import com.course.exception.EmployeeDeletionException;
import com.course.exception.EmployeeNotFoundException;
import com.course.repository.EmployeesRepository;
import com.course.entity.Employee;
import com.course.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeesServiceImpl implements EmployeesService {

    private final EmployeesRepository employeesRepository;

    @Autowired
    public EmployeesServiceImpl(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    @Override
    public List<Employee> employeesList() {
        return (List<Employee>) employeesRepository.findAll();
    }

    @Override
    public Employee findEmployeesById(Long id) {
        Optional<Employee> employees = employeesRepository.findById(id);

        if (employees.isPresent()) {
            return employees.get();
        } else {
            throw new EmployeeNotFoundException("Employee not found!");
        }
    }

    @Override
    public List<Employee> findEmployeesByName(String first, String last, String pather) {
        List<Employee> employees = (List<Employee>) employeesRepository.findByName(first, last, pather);

        if (!employees.isEmpty()) {
            return employees;
        } else {
            throw new EmployeeNotFoundException("Employee not found!");
        }
    }

    @Override
    public void deleteEmployeesById(Long id) {
        try{
            employeesRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new EmployeeNotFoundException("Employee not found!");
        } catch (DataIntegrityViolationException ex) {
            throw new EmployeeDeletionException("Employee can't be deleted!");
        }
    }

    @Override
    public Employee addEmployees(Employee employee) {
        return employeesRepository.save(employee);
    }

    @Override
    public Employee updateEmployees(Employee employee) {
        Optional<Employee> tmp = employeesRepository.findById(employee.getId());
        if (tmp.isPresent()) {
            return employeesRepository.save(employee);
        } else {
            throw new EmployeeNotFoundException("Employee not found!");
        }
    }
}
