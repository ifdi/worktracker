package com.worktracker.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Work {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private double hours;

    private LocalDate date;
}
