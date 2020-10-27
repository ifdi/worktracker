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
    private TaskType taskType;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNote() {
        return note;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public Project getProject() {
        return project;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
