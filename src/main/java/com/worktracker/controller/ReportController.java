package com.worktracker.controller;

import com.worktracker.model.dto.ReportByProjectResponseDTO;
import com.worktracker.model.dto.ReportByUserResponseDTO;
import com.worktracker.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
        return reportService.getReportByProject(id, LocalDate.parse(startDate), LocalDate.parse(endDate));
    }

    @GetMapping("/users/{id}")
    public List<ReportByUserResponseDTO> getReportByUser(@PathVariable Long id,
                                                         @RequestParam String startDate,
                                                         @RequestParam String endDate) {
        return reportService.getReportByUser(id, LocalDate.parse(startDate), LocalDate.parse(endDate));
    }
}
