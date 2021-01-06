package com.udacity.jdnd.course3.critter.model;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Employee",catalog ="critter")
public class Employee implements Serializable {

    @Id
    @SequenceGenerator(name="EMPLOYEE_SEQ", sequenceName="EMPLOYEE_SEQ", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator="EMPLOYEE_SEQ")
    @Column(name="EMPLOYEE_ID", nullable = false,unique = true)
    private long id;

    @Nationalized
    @Column(name="EMPLOYEE_NAME", nullable = false, length = 200)
    private String name;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Column(name = "DAYS_AVAILABLE", length = 300)
    private List<DayOfWeek> daysAvailable = new ArrayList<>();

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Column(name = "SKILLS", length = 300)
    private List<EmployeeSkill> skills = new ArrayList<>();





    public Employee(long id, String name, List<EmployeeSkill> skills, List<DayOfWeek> daysAvailable) {
        this.id = id;
        this.name = name;
        this.skills = skills;
        this.daysAvailable = daysAvailable;
    }

      public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(List<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public List<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(List<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    public Employee() {

    }
}
