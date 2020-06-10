package com.course.service.impl;

import com.course.exception.DepartmentDeletionException;
import com.course.exception.DepartmentNotFoundException;
import com.course.repository.DepartmentsRepository;
import com.course.entity.Department;
import com.course.service.DepartmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentsServiceImpl implements DepartmentsService {

    @Autowired
    private DepartmentsRepository departmentsRepository;

    @Override
    public List<Department> departmentsList() {
        return (List<Department>) departmentsRepository.findAll();
    }

    @Override
    public Department findDepartmentsById(Long id) {
        Optional<Department> department = departmentsRepository.findById(id);

        if (department.isPresent()) {
            return department.get();
        } else {
            throw new DepartmentNotFoundException("Department not found!");
        }
    }

    @Override
    public Department findDepartmentsByName(String name) {
        Department department = departmentsRepository.findByName(name);

        if (department != null) {
            return department;
        } else {
            throw new DepartmentNotFoundException("Department not found!");
        }
    }

    @Override
    public void deleteDepartments(Long id) {
        try{
            departmentsRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new DepartmentNotFoundException("Department nor found!");
        } catch (DataIntegrityViolationException ex) {
            throw new DepartmentDeletionException("Department can't be deleted!");
        }
    }

    @Override
    public Department addDepartments(Department department) {
        return departmentsRepository.save(department);
    }

    @Override
    public Department updateDepartments(Department department) {
        Optional<Department> tmp = departmentsRepository.findById(department.getId());
        if (tmp.isPresent()) {
            return departmentsRepository.save(department);
        } else {
            throw new DepartmentNotFoundException("Department nor found!");
        }
    }
}
