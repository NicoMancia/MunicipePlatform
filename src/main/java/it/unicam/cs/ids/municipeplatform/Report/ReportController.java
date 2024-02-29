package it.unicam.cs.ids.municipeplatform.Report;

import it.unicam.cs.ids.municipeplatform.DTOs.ReportEventCreationRequestDTO;
import it.unicam.cs.ids.municipeplatform.DTOs.ReportItineraryCreationRequestDTO;
import it.unicam.cs.ids.municipeplatform.DTOs.ReportPOICreationRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }
    @GetMapping(path ="/getByReportId/{id}")
    public ResponseEntity<?> getReportById(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.getReportById(id));
    }
    @GetMapping(path ="/getByEventId/{id}")
    public ResponseEntity<?> getAllReportsByIdEvent(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.getAllReportsByIdEvent(id));
    }
    @GetMapping(path ="/getByPoiId/{id}")
    public ResponseEntity<?> getAllReportsByIdPOI(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.getAllReportsByIdPOI(id));
    }
    @GetMapping(path ="/getByItineraryId/{id}")
    public ResponseEntity<?> getAllReportsByIdItinerary(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.getAllReportsByIdItinerary(id));
    }
    @GetMapping("/getAllPending")
    public ResponseEntity<List<ReportEntity>> getAllPending() {
        return ResponseEntity.ok(reportService.getAllPendingReports());
    }
    @PostMapping("/createReportEve")
    public ResponseEntity<?> createReportEvent (@RequestBody ReportEventCreationRequestDTO dto)
    {
        ReportEntity newReport = reportService.createReport(new ReportEntity(dto));
        return ResponseEntity.ok("Report successfully created.");
    }
    @PostMapping("/createReportPOI")
    public ResponseEntity<?> createReportPOI (@RequestBody ReportPOICreationRequestDTO dto)
    {
        ReportEntity newReport = reportService.createReport(new ReportEntity(dto));
        return ResponseEntity.ok("Report successfully created.");
    }
    @PostMapping("/createReportItn")
    public ResponseEntity<?> createReportItinerary (@RequestBody ReportItineraryCreationRequestDTO dto)
    {
        ReportEntity newReport = reportService.createReport(new ReportEntity(dto));
        return ResponseEntity.ok("Report successfully created.");
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id)
    {
        reportService.deleteReport(id);
        return ResponseEntity.ok("Report successfully deleted.");
    }
}
