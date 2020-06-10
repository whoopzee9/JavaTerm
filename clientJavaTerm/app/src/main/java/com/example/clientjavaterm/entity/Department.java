package com.example.clientjavaterm.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Department {
    private Long id;
    private String name;

    public Department(Long id, String name) {
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

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Department dep = (Department) obj;
        return name.equals(dep.name);
    }
}
