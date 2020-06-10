package com.course.entity;

import javax.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(length = 20, name = "first_name", nullable = false)
    private String firstName;

    @Column(length = 20, name = "last_name", nullable = false)
    private String lastName;

    @Column(length = 20, name = "pather_name", nullable = false)
    private String patherName;

    @Column(length = 50, nullable = false)
    private String position;

    @Column
    private Float salary;

    public Employee() {
    }

    public Employee(String firstName, String lastName, String patherName, String position, Float salary) {
        if (salary < 0) {
            throw new IllegalArgumentException("Salary can't be lower that 0!");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.patherName = patherName;
        this.position = position;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatherName() {
        return patherName;
    }

    public void setPatherName(String patherName) {
        this.patherName = patherName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee { " + id + " ; " + firstName + " ; " + lastName + " ; " + patherName + " ; " +
                position + " ; " + salary + " }";
    }
}
