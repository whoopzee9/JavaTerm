package com.course.service;

import com.course.entity.Employees;

import java.util.List;

public interface EmployeesService {
    List<Employees> employeesList();
    Employees findEmployeesById(Long id);
    List<Employees> findEmployeesByName(String first, String last, String pather);
    void deleteEmployeesById(Long id);
    Employees addEmployees(Employees employees);
    Employees updateEmployees(Employees employees);
}
