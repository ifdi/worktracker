package com.worktracker.service.impl;

import com.worktracker.model.dto.ReportByProjectResponseDTO;
import com.worktracker.model.dto.ReportByUserResponseDTO;
import com.worktracker.repository.WorkRepository;
import com.worktracker.repository.projection.ReportByProjectProjection;
import com.worktracker.repository.projection.ReportByUserProjection;
import com.worktracker.service.ReportService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    private final WorkRepository workRepository;

    public ReportServiceImpl(WorkRepository workRepository) {
        this.workRepository = workRepository;
    }

    @Override
    public List<ReportByProjectResponseDTO> getReportByProject(Integer projectId, LocalDate startDate, LocalDate endDate) {
        List<ReportByProjectProjection> projectionList = workRepository.getReportByProject(projectId, startDate, endDate);
        Map<Long, ReportByProjectResponseDTO> responseDTOMap = new HashMap<>();

        for (ReportByProjectProjection projection : projectionList) {
            if (!responseDTOMap.containsKey(projection.getTaskId())) {
                ReportByProjectResponseDTO reportByProjectResponseDTO = new ReportByProjectResponseDTO();
                reportByProjectResponseDTO.setTypeTask(projection.getTypeTask());
                reportByProjectResponseDTO.setTaskName(projection.getTaskName());

                responseDTOMap.put(projection.getTaskId(), reportByProjectResponseDTO);
            }
            responseDTOMap.get(projection.getTaskId()).addUserHours(projection.getUserId(),
                    projection.getUserName(), projection.getHours());
        }
        return new ArrayList<>(responseDTOMap.values());
    }

    @Override
    public List<ReportByUserResponseDTO> getReportByUser(Long userId, LocalDate startDate, LocalDate endDate) {
        List<ReportByUserProjection> projectionList = workRepository.getReportByUser(userId, startDate, endDate);
        Map<Integer, ReportByUserResponseDTO> responseDTOMAp = new HashMap<>();

        for (ReportByUserProjection projection : projectionList) {
            if (!responseDTOMAp.containsKey(projection.getProjectId())) {
                ReportByUserResponseDTO reportByUserResponseDTO = new ReportByUserResponseDTO();
                reportByUserResponseDTO.setProjectId(projection.getProjectId());
                reportByUserResponseDTO.setProjectName(projection.getProjectName());

                responseDTOMAp.put(projection.getProjectId(), reportByUserResponseDTO);
            }
            responseDTOMAp.get(projection.getProjectId()).addTaskDTO(projection.getTaskId(),
                    projection.getTypeTask(), projection.getTaskName(), projection.getHours());
        }
        return new ArrayList<>(responseDTOMAp.values());
    }
}
