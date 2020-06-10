package com.course.service;

import com.course.entity.DepartmentEmployee;

import java.util.List;

public interface DepartmentsEmployeesService {
    List<DepartmentEmployee> departmentsEmployeesList();
    List<DepartmentEmployee> findByDepartmentId(Long id);
    List<DepartmentEmployee> findByDepartmentName(String name);
    List<DepartmentEmployee> findByEmployeeId(Long id);
    List<DepartmentEmployee> findByEmployeeName(String first, String last, String pather);
    void deleteDepartmentEmployee(Long id);
    DepartmentEmployee addDepartmentsEmployees(DepartmentEmployee departmentEmployee);
    DepartmentEmployee updateDepartmentsEmployees(DepartmentEmployee departmentEmployee);
}
