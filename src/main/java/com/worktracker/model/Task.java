package com.worktracker.model;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String note;

    @Enumerated(EnumType.STRING)
    private TypeTask typeTask;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
