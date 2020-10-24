package com.worktracker.model.dto;

import java.time.LocalDate;

public class WorkRequestDTO {

    private Long userId;
    private double hours;
    private LocalDate date;

    public Long getUserId() {
        return userId;
    }

    public double getHours() {
        return hours;
    }

    public LocalDate getDate() {
        return date;
    }
}
