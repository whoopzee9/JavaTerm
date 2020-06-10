package com.example.clientjavaterm.entity;

import androidx.annotation.Nullable;

public class DepartmentEmployee {
    private Long id;
    private Department department;
    private Employee employee;

    public DepartmentEmployee(Long id, Department department, Employee employee) {
        this.id = id;
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

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        DepartmentEmployee de = (DepartmentEmployee) obj;
        return department.equals(de.department) &&
                employee.equals(de.employee);
    }
}
