package com.course.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "projects")
public class Projects {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column(length = 200, nullable = false)
    private String name;

    @Column
    private Float cost;

    @ManyToOne(targetEntity = Departments.class)
    @JoinColumn(name = "department_id", nullable = false)
    private Departments departments;

    @Column(name = "date_beg")
    private Date dateBeg;

    @Column(name = "date_end")
    private Date dateEnd;

    @Column(name = "date_end_real")
    private Date dateEndReal;

    public Projects() {
    }

    public Projects(String name, Float cost, Departments departments, Date dateBeg, Date dateEnd, Date dateEndReal) {
        this.name = name;
        this.cost = cost;
        this.departments = departments;
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

    public Departments getDepartments() {
        return departments;
    }

    public void setDepartments(Departments departments) {
        this.departments = departments;
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
    public String toString() {
        return "Project { " + id + " ; " + name + " ; " + cost + " ; " + departments.getName() + " ; " +
                dateBeg + " ; " + dateEnd + " ; " + dateEndReal + " }";
    }
}
