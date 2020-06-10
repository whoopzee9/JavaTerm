package com.course.service;

import com.course.entity.Employee;

import java.util.List;

public interface EmployeesService {
    List<Employee> employeesList();
    Employee findEmployeesById(Long id);
    List<Employee> findEmployeesByName(String first, String last, String pather);
    void deleteEmployeesById(Long id);
    Employee addEmployees(Employee employee);
    Employee updateEmployees(Employee employee);
}
