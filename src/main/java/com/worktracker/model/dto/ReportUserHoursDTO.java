package com.worktracker.model.dto;

import java.util.Objects;

public class ReportUserHoursDTO {

    private final Long userId;
    private final String userName;
    private Double hours;

    public ReportUserHoursDTO(Long userId, String userName, Double hours) {
        this.userName = userName;
        this.userId = userId;
        this.hours = Objects.requireNonNullElse(hours, 0.0);
    }

    public String getUserName() {
        return userName;
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

    public Long getUserId() {
        return userId;
    }

    public void addHours(Double hours) {
        this.hours += hours;
    }
}
