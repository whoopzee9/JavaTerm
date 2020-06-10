package com.course.service;

import com.course.entity.Department;

import java.util.List;

public interface DepartmentsService {
    List<Department> departmentsList();
    Department findDepartmentsById(Long id);
    Department findDepartmentsByName(String name);
    void deleteDepartments(Long id);
    Department addDepartments(Department department);
    Department updateDepartments(Department department);
}
