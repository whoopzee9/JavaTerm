package com.example.clientjavaterm.entity;

import androidx.annotation.NonNull;

public class Employees {
    private Long id;
    private String firstName;
    private String lastName;
    private String patherName;
    private String position;
    private Float salary;

    public Employees(Long id, String firstName, String lastName, String patherName, String position, Float salary) {
        this.id = id;
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

    @NonNull
    @Override
    public String toString() {
        return "[ " + id + ", " + lastName + ", " + firstName + ", " + patherName + " ]";
    }
}
