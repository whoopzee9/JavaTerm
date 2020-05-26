package com.course.service;

import com.course.entity.DepartmentsEmployees;

import java.util.List;

public interface DepartmentsEmployeesService {
    List<DepartmentsEmployees> departmentsEmployeesList();
    List<DepartmentsEmployees> findByDepartmentId(Long id);
    List<DepartmentsEmployees> findByDepartmentName(String name);
    List<DepartmentsEmployees> findByEmployeeId(Long id);
    List<DepartmentsEmployees> findByEmployeeName(String first, String last, String pather);
    void deleteDepartmentsEmployeesByIds(Long eId, Long dId);
    void deleteDepartmentsEmployeesByNames(String last, String first, String pather, String dName);
    DepartmentsEmployees addDepartmentsEmployees(DepartmentsEmployees departmentsEmployees);
    DepartmentsEmployees updateDepartmentsEmployees(DepartmentsEmployees departmentsEmployees);
}
