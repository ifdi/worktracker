package com.worktracker.integration.controller;

import com.worktracker.integration.BaseIT;
import com.worktracker.repository.TokenRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql({"classpath:sql/reports/insert-report-data.sql"})
@Sql(value = {"classpath:sql/reports/delete-report-data.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ReportControllerIT extends BaseIT {

    @Autowired
    private TokenRepository tokenRepository;

    @Test
    public void getReportByProjectSuccess() throws Exception {
        assertTrue(tokenRepository.existsByToken("OQ_2nG-BYHlhQlCC"));
        params.add("startDate", "2020-09-05");
        params.add("endDate", "2020-11-05");

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/reports/projects/{id}", 1)
                        .header("Authorization", "OQ_2nG-BYHlhQlCC")
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
        assertTrue(tokenRepository.existsByToken("OQ_2nG-BYHlhQlCC"));
        params.add("startDate", "2020-09-05");
        params.add("endDate", "2020-11-05");

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/reports/projects/{id}", 2)
                        .header("Authorization", "OQ_2nG-BYHlhQlCC")
                        .params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getReportByProjectMissingDate() throws Exception {
        assertTrue(tokenRepository.existsByToken("OQ_2nG-BYHlhQlCC"));
        params.add("startDate", "2020-09-05");

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/reports/projects/{id}", 1)
                        .header("Authorization", "OQ_2nG-BYHlhQlCC")
                        .params(params))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getReportByProjectInvalidDate() throws Exception {
        assertTrue(tokenRepository.existsByToken("OQ_2nG-BYHlhQlCC"));
        params.add("startDate", "2020-09-05");
        params.add("endDate", "invalid date");

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/reports/projects/{id}", 1)
                        .header("Authorization", "OQ_2nG-BYHlhQlCC")
                        .params(params))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getReportByProjectMissingHeader() throws Exception {
        params.add("startDate", "2020-09-05");
        params.add("endDate", "2020-11-05");

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/reports/projects/{id}", 1)
                        .params(params))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getReportByProjectNonExistingAuth() throws Exception {
        assertFalse(tokenRepository.existsByToken("OQ_2nG-BYHlhQlCZ"));
        params.add("startDate", "2020-09-05");
        params.add("endDate", "2020-11-05");

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/reports/projects/{id}", 1)
                        .header("Authorization", "OQ_2nG-BYHlhQlCZ")
                        .params(params))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getReportByUserSuccess() throws Exception {
        assertTrue(tokenRepository.existsByToken("OQ_2nG-BYHlhQlCC"));
        params.add("startDate", "2020-09-05");
        params.add("endDate", "2020-11-05");

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/reports/users/{id}", 1)
                        .header("Authorization", "OQ_2nG-BYHlhQlCC")
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
        assertTrue(tokenRepository.existsByToken("OQ_2nG-BYHlhQlCC"));
        params.add("startDate", "2020-09-05");
        params.add("endDate", "2020-11-05");

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/reports/users/{id}", 2)
                        .header("Authorization", "OQ_2nG-BYHlhQlCC")
                        .params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getReportByUserMissingDate() throws Exception {
        assertTrue(tokenRepository.existsByToken("OQ_2nG-BYHlhQlCC"));
        params.add("startDate", "2020-09-05");

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/reports/users/{id}", 1)
                        .header("Authorization", "OQ_2nG-BYHlhQlCC")
                        .params(params))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getReportByUserInvalidDate() throws Exception {
        assertTrue(tokenRepository.existsByToken("OQ_2nG-BYHlhQlCC"));
        params.add("startDate", "2020-09-05");
        params.add("endDate", "invalid date");

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/reports/users/{id}", 1)
                        .header("Authorization", "OQ_2nG-BYHlhQlCC")
                        .params(params))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getReportByUserMissingHeader() throws Exception {
        params.add("startDate", "2020-09-05");
        params.add("endDate", "2020-11-05");

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/reports/users/{id}", 2)
                        .params(params))
                .andExpect(status().isBadRequest());
    }
}
