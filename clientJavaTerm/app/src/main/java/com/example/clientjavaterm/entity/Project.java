package com.example.clientjavaterm.entity;

import androidx.annotation.Nullable;

import java.sql.Date;

public class Project {
    private Long id;
    private String name;
    private Float cost;
    private Department department;
    private Date dateBeg;
    private Date dateEnd;
    private Date dateEndReal;

    public Project(Long id, String name, Float cost, Department department, Date dateBeg, Date dateEnd, Date dateEndReal) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.department = department;
        this.dateBeg = dateBeg;
        this.dateEnd = dateEnd;
        this.dateEndReal = dateEndReal;
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

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Date getDateBeg() {
        return dateBeg;
    }

    public void setDateBeg(Date dateBeg) {
        this.dateBeg = dateBeg;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Date getDateEndReal() {
        return dateEndReal;
    }

    public void setDateEndReal(Date dateEndReal) {
        this.dateEndReal = dateEndReal;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Project proj = (Project) obj;
        return name.equals(proj.name) &&
                cost.equals(proj.cost) &&
                department.equals(proj.department) &&
                dateBeg.equals(proj.dateBeg) &&
                dateEnd.equals(proj.dateEnd) &&
                dateEndReal.equals(proj.dateEndReal);
    }
}
