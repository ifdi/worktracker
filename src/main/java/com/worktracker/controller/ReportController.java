package com.worktracker.controller;

import com.worktracker.exception.WorktrackerException;
import com.worktracker.model.dto.ReportByProjectResponseDTO;
import com.worktracker.model.dto.ReportByUserResponseDTO;
import com.worktracker.service.AuthorizationService;
import com.worktracker.service.ReportService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Api(tags = "Report Controller")
@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;
    private final AuthorizationService authorizationService;

    @Autowired
    public ReportController(ReportService reportService, AuthorizationService authorizationService) {
        this.reportService = reportService;
        this.authorizationService = authorizationService;
    }

    @GetMapping("/projects/{id}")
    public List<ReportByProjectResponseDTO> getReportByProject(@PathVariable Integer id,
                                                               @RequestParam String startDate,
                                                               @RequestParam String endDate,
                                                               @RequestHeader("Authorization") String token) {
        authorizationService.validateToken(token);
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
                                                         @RequestParam String endDate,
                                                         @RequestHeader("Authorization") String token) {
        authorizationService.validateToken(token);
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
