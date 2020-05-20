package com.course.entity;

import javax.persistence.*;

@Entity
@Table(name = "departments_employees")
public class DepartmentsEmployees {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @ManyToOne(targetEntity = Departments.class)
    @JoinColumn(name = "department_id", nullable = false)
    private Departments departments;

    @ManyToOne(targetEntity = Employees.class)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employees employees;

    public DepartmentsEmployees() {
    }

    public DepartmentsEmployees(Departments departments, Employees employees) {
        this.departments = departments;
        this.employees = employees;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Departments getDepartments() {
        return departments;
    }

    public void setDepartments(Departments departments) {
        this.departments = departments;
    }

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }
}
