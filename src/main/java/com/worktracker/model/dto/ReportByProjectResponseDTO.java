package com.worktracker.model.dto;

import com.worktracker.model.TypeTask;

import java.util.ArrayList;
import java.util.List;

public class ReportByProjectResponseDTO {

    private final List<ReportUserHoursDTO> users = new ArrayList<>();
    private TypeTask typeTask;
    private String taskName;
    private Double totalHoursWork = 0.0;

    public Double getTotalHoursWork() {
        this.calculateTotalHours();
        return totalHoursWork;
    }

    public TypeTask getTypeTask() {
        return typeTask;
    }

    public void setTypeTask(TypeTask typeTask) {
        this.typeTask = typeTask;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public List<ReportUserHoursDTO> getUsers() {
        return this.users;
    }

    private void calculateTotalHours() {
        double sum = 0;
        for (ReportUserHoursDTO u : users) {
            sum += u.getHours();
        }
        this.totalHoursWork = sum;
    }

    public void addUserHours(Long userId, String username, Double hours) {
        for (ReportUserHoursDTO u : users) {
            if (u.getUserId().equals(userId)) {
                u.addHours(hours);
                return;
            }
        }
        users.add(new ReportUserHoursDTO(userId, username, hours));
    }
}
