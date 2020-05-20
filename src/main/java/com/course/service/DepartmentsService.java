package com.course.service;

import com.course.entity.Departments;

import java.util.List;

public interface DepartmentsService {
    List<Departments> departmentsList();
    Departments findDepartmentsById(Long id);
    Departments findDepartmentsByName(String name);
    void deleteDepartments(Long id);
    Departments addDepartments(Departments departments);
    Departments updateDepartments(Departments departments);
}
