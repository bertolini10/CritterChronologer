package com.udacity.jdnd.course3.critter.model;


import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="Schedule",catalog ="critter")
public class Schedule implements Serializable {

    @Id
    @SequenceGenerator(name="SCHEDULE_SEQ", sequenceName="SCHEDULE_SEQ", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator="SCHEDULE_SEQ")
    @Column(name="SCHEDULE_ID", nullable = false,unique = true)
    private long id;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Column(name = "ACTIVITIES", length = 400)
    private Set<EmployeeSkill> activities = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "SCHEDULE_PET", joinColumns = @JoinColumn(name = "SCHEDULE_ID"),
            inverseJoinColumns = @JoinColumn(name = "PET_ID"))
    private List<Pet> pets = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "SCHEDULE_EMPLOYEE", joinColumns = @JoinColumn(name = "SCHEDULE_ID"),
            inverseJoinColumns = @JoinColumn(name = "EMPLOYEE_ID"))
    private List<Employee> employees = new ArrayList<>();


    @Column(name="SCHEDULE_DATE")
    private LocalDate date;




    public Schedule(long id, Set<EmployeeSkill> activities, LocalDate date, List<Employee> employees, List<Pet> pets) {
        this.id = id;
        this.activities = activities;
        this.date = date;
        this.employees = employees;
        this.pets = pets;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }


    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public Schedule() {

    }
}