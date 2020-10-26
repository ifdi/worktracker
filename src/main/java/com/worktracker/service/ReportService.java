package com.worktracker.service;

import com.worktracker.model.dto.ReportByProjectResponseDTO;
import com.worktracker.model.dto.ReportByUserResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {

    List<ReportByProjectResponseDTO> getReportByProject(Integer projectId, LocalDate startDate, LocalDate endDate);

    List<ReportByUserResponseDTO> getReportByUser(Long userId, LocalDate startDate, LocalDate endDate);
}
