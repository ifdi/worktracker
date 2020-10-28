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

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
