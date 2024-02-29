package it.unicam.cs.ids.municipeplatform.Report;

import it.unicam.cs.ids.municipeplatform.Content.ContentEntity;

import java.util.List;

public interface ReportService {
    ReportEntity getReportById(Long id);
    List<ReportEntity> getAllReportsByIdEvent(Long contentId);
    List<ReportEntity> getAllReportsByIdItinerary(Long contentId);
    List<ReportEntity> getAllReportsByIdPOI(Long contentId);
    List<ReportEntity> getAllPendingReports();
    ReportEntity createReport(ReportEntity report);
    void deleteReport(Long id);
}
