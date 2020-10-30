package com.worktracker.controller;

import com.worktracker.exception.WorktrackerException;
import com.worktracker.model.dto.ReportByProjectResponseDTO;
import com.worktracker.model.dto.ReportByUserResponseDTO;
import com.worktracker.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/projects/{id}")
    public List<ReportByProjectResponseDTO> getReportByProject(@PathVariable Integer id,
                                                               @RequestParam String startDate,
                                                               @RequestParam String endDate) {
        LocalDate start;
        LocalDate end;
        try {
            start = LocalDate.parse(startDate);
            end = LocalDate.parse(endDate);
        } catch (DateTimeParseException e) {
            throw new WorktrackerException("Invalid date");
        }

        return reportService.getReportByProject(id, start, end);
    }

    @GetMapping("/users/{id}")
    public List<ReportByUserResponseDTO> getReportByUser(@PathVariable Long id,
                                                         @RequestParam String startDate,
                                                         @RequestParam String endDate) {
        LocalDate start;
        LocalDate end;
        try {
            start = LocalDate.parse(startDate);
            end = LocalDate.parse(endDate);
        } catch (DateTimeParseException e) {
            throw new WorktrackerException("Invalid date");
        }
        return reportService.getReportByUser(id, start, end);
    }
}
