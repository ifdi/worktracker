package com.worktracker.unit.service;

import com.worktracker.model.Task;
import com.worktracker.model.TaskType;
import com.worktracker.model.dto.ReportByProjectResponseDTO;
import com.worktracker.model.dto.ReportByUserResponseDTO;
import com.worktracker.model.dto.ReportTaskDTO;
import com.worktracker.model.dto.ReportUserHoursDTO;
import com.worktracker.repository.WorkRepository;
import com.worktracker.repository.projection.ReportByProjectProjection;
import com.worktracker.repository.projection.ReportByUserProjection;
import com.worktracker.service.ReportService;
import com.worktracker.service.impl.ReportServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RepostServiceTest {

    private ReportService reportService;

    @Mock
    private WorkRepository workRepository;

    @Before
    public void setUp() {
        reportService = new ReportServiceImpl(workRepository);
    }

    @Test
    public void getReportByProjectSuccess() {
        ReportByProjectProjection reportByProjectProjection1 = getReportByProjectProjection();
        ReportByProjectProjection reportByProjectProjection2 = getReportByProjectProjection();
        LocalDate startDate = LocalDate.parse("2020-11-05");
        LocalDate endDate = LocalDate.parse("2020-11-06");
        when(workRepository.getReportByProject(1, startDate, endDate))
                .thenReturn(List.of(reportByProjectProjection1, reportByProjectProjection2));

        List<ReportByProjectResponseDTO> resultList = reportService
                .getReportByProject(1, startDate, endDate);
        ReportByProjectResponseDTO resultDto = resultList.get(0);

        assertNotNull(resultList);
        assertEquals(1, resultList.size());
        assertEquals(TaskType.RC, resultDto.getTaskType());
        assertEquals("Task Test Name", resultDto.getTaskName());
        assertEquals(3, resultDto.getTotalHoursWork(), 0);
        assertEquals(1, resultDto.getUsers().size());
        ReportUserHoursDTO users = resultDto.getUsers().get(0);
        assertEquals(Long.valueOf(1L), users.getUserId());
        assertEquals("User Test Name", users.getUserName());
        assertEquals(3, users.getHours(), 0);
    }

    @Test
    public void getReportByUserSuccess() {
        ReportByUserProjection reportByUserProjection1 = getReportByUserProjection();
        ReportByUserProjection reportByUserProjection2 = getReportByUserProjection();
        LocalDate startDate = LocalDate.parse("2020-11-05");
        LocalDate endDate = LocalDate.parse("2020-11-06");
        when(workRepository.getReportByUser(1L, startDate, endDate))
                .thenReturn(List.of(reportByUserProjection1, reportByUserProjection2));

        List<ReportByUserResponseDTO> resultList = reportService
                .getReportByUser(1L, startDate, endDate);
        ReportByUserResponseDTO resultDto = resultList.get(0);

        assertNotNull(resultList);
        assertEquals(1, resultList.size());
        assertEquals(Integer.valueOf(1), resultDto.getProjectId());
        assertEquals("Project Test Name", resultDto.getProjectName());
        assertEquals(3.0, resultDto.getProjectHours(), 0);
        assertEquals(1, resultDto.getTasks().size());
        ReportTaskDTO tasks = resultDto.getTasks().get(0);
        assertEquals(Long.valueOf(1L), tasks.getTaskId());
        assertEquals(TaskType.ENG, tasks.getTaskType());
        assertEquals("Task Test Name", tasks.getTaskName());
        assertEquals(3.0, tasks.getTaskHours(), 0);
    }

    private ReportByProjectProjection getReportByProjectProjection() {
        return new ReportByProjectProjection() {
            @Override
            public Long getTaskId() {
                return 1L;
            }

            @Override
            public TaskType getTaskType() {
                return TaskType.RC;
            }

            @Override
            public String getTaskName() {
                return "Task Test Name";
            }

            @Override
            public Double getHours() {
                return 1.5;
            }

            @Override
            public String getUserName() {
                return "User Test Name";
            }

            @Override
            public Long getUserId() {
                return 1L;
            }
        };
    }

    private ReportByUserProjection getReportByUserProjection() {
        return new ReportByUserProjection() {
            @Override
            public Integer getProjectId() {
                return 1;
            }

            @Override
            public String getProjectName() {
                return "Project Test Name";
            }

            @Override
            public TaskType getTaskType() {
                return TaskType.ENG;
            }

            @Override
            public String getTaskName() {
                return "Task Test Name";
            }

            @Override
            public Double getHours() {
                return 1.5;
            }

            @Override
            public LocalDate getDate() {
                return LocalDate.parse("2020-11-05");
            }

            @Override
            public Long getTaskId() {
                return 1L;
            }
        };
    }

}