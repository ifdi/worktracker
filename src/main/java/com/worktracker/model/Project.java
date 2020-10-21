package com.worktracker.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    private int id;

    private String name;
}
