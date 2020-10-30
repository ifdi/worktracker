package com.worktracker.unit.service;

import com.worktracker.exception.WorktrackerException;
import com.worktracker.model.Project;
import com.worktracker.model.dto.ProjectRequestDTO;
import com.worktracker.repository.ProjectRepository;
import com.worktracker.service.ProjectService;
import com.worktracker.service.impl.ProjectServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {

    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Before
    public void setUp() {
        projectService = new ProjectServiceImpl(projectRepository);
    }

    @Test
    public void createProjectSuccess() {
        ProjectRequestDTO projectRequestDTO = new ProjectRequestDTO();
        projectRequestDTO.setId(1);
        projectRequestDTO.setName("Test name");
        when(projectRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Project result = projectService.createProject(projectRequestDTO);

        assertNotNull(result);
        assertEquals(Integer.valueOf(1), result.getId());
        assertEquals("Test name", result.getName());
    }

    @Test
    public void updateProjectNameSuccess() {
        Project project = new Project();
        when(projectRepository.findById(1)).thenReturn(Optional.of(project));

        projectService.updateProjectName(1, "Test name");

        verify(projectRepository, times(1)).save(project);
        assertEquals("Test name", project.getName());
    }

    @Test(expected = WorktrackerException.class)
    public void updateProjectNameNonExist() {
        when(projectRepository.findById(1)).thenReturn(Optional.empty());

        projectService.updateProjectName(1, "name");

        verify(projectRepository, times(0)).save(any());
    }

    @Test
    public void getProjectExist() {
        Project project = new Project();
        project.setName("Test name");
        when(projectRepository.findById(1)).thenReturn(Optional.of(project));

        Project result = projectService.getProject(1);

        assertNotNull(result);
        assertEquals("Test name", result.getName());
    }

    @Test(expected = WorktrackerException.class)
    public void getProjectNonExist() {
        when(projectRepository.findById(1)).thenReturn(Optional.empty());

        projectService.getProject(1);
    }

    @Test
    public void getAllProjectsSuccess() {
        Project project = new Project();
        project.setName("Test name");
        when(projectRepository.findAll()).thenReturn(List.of(project));

        List<Project> result = projectService.getAllProjects();

        assertEquals(1, result.size());
        assertEquals("Test name", result.get(0).getName());
    }

}
