package com.course.entity;

import javax.persistence.*;

@Entity
@Table(name = "departments_employees")
public class DepartmentEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne(targetEntity = Department.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne(targetEntity = Employee.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    public DepartmentEmployee() {
    }

    public DepartmentEmployee(Department department, Employee employee) {
        this.department = department;
        this.employee = employee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
