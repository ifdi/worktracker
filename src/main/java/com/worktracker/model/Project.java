package com.worktracker.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "project")
    private List<Task> tasks;
}
