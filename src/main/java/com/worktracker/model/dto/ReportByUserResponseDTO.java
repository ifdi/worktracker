package com.worktracker.model.dto;

import com.worktracker.model.TaskType;

import java.util.ArrayList;
import java.util.List;

public class ReportByUserResponseDTO {

    private final List<ReportTaskDTO> tasks = new ArrayList<>();
    private Integer projectId;
    private String projectName;
    private Double projectHours;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Double getProjectHours() {
        calculateProjectHours();
        return projectHours;
    }

    public void addTaskDTO(Long taskId, TaskType taskType, String taskName, Double taskHours) {
        for (ReportTaskDTO t : tasks) {
            if (t.getTaskId().equals(taskId)) {
                t.addHours(taskHours);
                return;
            }
        }
        tasks.add(new ReportTaskDTO(taskId, taskType, taskName, taskHours));
    }

    private void calculateProjectHours() {
        double sum = 0;
        for (ReportTaskDTO t : tasks) {
            sum += t.getTaskHours();
        }
        this.projectHours = sum;
    }

    public List<ReportTaskDTO> getTasks() {
        return tasks;
    }
}
