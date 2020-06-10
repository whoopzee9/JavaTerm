package com.course.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(length = 200, nullable = false)
    private String name;

    @Column
    private Float cost;

    @ManyToOne(targetEntity = Department.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(name = "date_beg", length = 10)
    private Date dateBeg;

    @Column(name = "date_end", length = 10)
    private Date dateEnd;

    @Column(name = "date_end_real", length = 10)
    private Date dateEndReal;

    public Project() {
    }

    public Project(String name, Float cost, Department department, Date dateBeg, Date dateEnd, Date dateEndReal) {
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
    public String toString() {
        return "Project { " + id + " ; " + name + " ; " + cost + " ; " + department.getName() + " ; " +
                dateBeg + " ; " + dateEnd + " ; " + dateEndReal + " }";
    }
}
