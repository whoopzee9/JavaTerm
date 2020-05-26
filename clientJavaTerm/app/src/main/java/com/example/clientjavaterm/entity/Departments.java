package com.example.clientjavaterm.entity;

import androidx.annotation.NonNull;

public class Departments {
    private Long id;
    private String name;

    public Departments(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return "[ " + id + ", " + name + " ]";
    }
}
