package pl.io.emergency.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.io.emergency.entity.Report;
import pl.io.emergency.entity.ReportType;
import pl.io.emergency.service.ReportService;

import java.time.LocalDate;

/**
 * Controller managing API endpoints for generating reports about giver, resources, emergencies, history, etc.
 */
@RestController
@RequestMapping("/api/report")
public class ReportController {
    private static final Logger log = LoggerFactory.getLogger(ReportController.class);
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
        log.info("Service instantiated");
    }


    @Operation(summary = "Generate report for a Giver", description = "Creates a report containing information about donations a specific Giver has made," +
            " and saves it in the database")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Report created, returned, saved."),
    })
    @Tag(name = "Report Generation", description = "Operations related to generating reports")
    @PostMapping("/getGiver")
    public ResponseEntity<Report> getGiverReport(@RequestParam(required = true) long giverId) {
        log.info("Giver report requested with id {}", giverId);
        Report report = reportService.getGiverReport(giverId);
        log.info("Report created: {}", report.getDatabaseData());
        return ResponseEntity.status(HttpStatus.OK).body(report);
    }



    @Operation(summary = "Generate report for the Government", description = "Creates different types of reports based on the type, for the government.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Report created, returned."),
    })
    @Tag(name = "Report Generation", description = "Operations related to generating reports")
    @PostMapping("/getGovernment")
    public ResponseEntity<Report> getGovernmentReport(@Parameter
    (
            description = "Type of report to generate.",
            schema = @Schema(allowableValues = {"ACTIVE_NGO_RESOURCES", "ARCHIVE_NGO_RESOURCES", "ACTIVE_VOLUNTEERS", "ARCHIVE_VOLUNTEERS", "ACTIVE_CATASTROPHES", "ARCHIVE_CATASTROPHES"}))
            @RequestParam(required = true) ReportType reportType,
            @RequestParam(required = true) LocalDate dateFrom,
            @RequestParam(required = true) LocalDate dateTo
    )
    {
        log.info("Government report requested with type {}, dateFrom {}, dateTo {}", reportType, dateFrom, dateTo);
        Report report = reportService.getGovernmentReport(reportType, dateFrom, dateTo);
        return ResponseEntity.status(HttpStatus.OK).body(report);
    }
}
