package com.worktracker.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.worktracker.integration.BaseIT;
import com.worktracker.repository.WorkRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql({"classpath:sql/reports/insert-report-data.sql"})
@Sql(value = {"classpath:sql/reports/delete-report-data.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ReportControllerIT extends BaseIT {

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getReportByProjectSuccess() throws Exception {
        params.add("startDate", "2020-09-05");
        params.add("endDate", "2020-11-05");

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/reports/projects/{id}", 1)
                        .params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].taskType", is("RC")))
                .andExpect(jsonPath("$[0].taskName", is("task name 1")))
                .andExpect(jsonPath("$[0].totalHoursWork", is(5.0)))
                .andExpect(jsonPath("$[0].users", hasSize(1)))
                .andExpect(jsonPath("$[0].users[0].userId", is(1)))
                .andExpect(jsonPath("$[0].users[0].userName", is("User name 1")))
                .andExpect(jsonPath("$[0].users[0].hours", is(5.0)));
    }

    @Test
    public void getReportByProjectSuccessMissingProject() throws Exception {
        params.add("startDate", "2020-09-05");
        params.add("endDate", "2020-11-05");

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/reports/projects/{id}", 2)
                        .params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getReportByProjectMissingDate() throws Exception {
        params.add("startDate", "2020-09-05");

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/reports/projects/{id}", 1)
                        .params(params))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getReportByProjectInvalidDate() throws Exception {
        params.add("startDate", "2020-09-05");
        params.add("endDate", "invalid date");

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/reports/projects/{id}", 1)
                        .params(params))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getReportByUserSuccess() throws Exception {
        params.add("startDate", "2020-09-05");
        params.add("endDate", "2020-11-05");

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/reports/users/{id}", 1)
                        .params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].projectId", is(1)))
                .andExpect(jsonPath("$[0].projectName", is("project name 1")))
                .andExpect(jsonPath("$[0].projectHours", is(5.0)))
                .andExpect(jsonPath("$[0].tasks", hasSize(1)))
                .andExpect(jsonPath("$[0].tasks[0].taskId", is(1)))
                .andExpect(jsonPath("$[0].tasks[0].taskType", is("RC")))
                .andExpect(jsonPath("$[0].tasks[0].taskName", is("task name 1")))
                .andExpect(jsonPath("$[0].tasks[0].taskHours", is(5.0)));
    }

    @Test
    public void getReportByUserSuccessMissingUser() throws Exception {
        params.add("startDate", "2020-09-05");
        params.add("endDate", "2020-11-05");

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/reports/users/{id}", 2)
                        .params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getReportByUserMissingDate() throws Exception {
        params.add("startDate", "2020-09-05");

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/reports/users/{id}", 1)
                        .params(params))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getReportByUserInvalidDate() throws Exception {
        params.add("startDate", "2020-09-05");
        params.add("endDate", "invalid date");

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/reports/users/{id}", 1)
                        .params(params))
                .andExpect(status().isBadRequest());
    }

}
