package it.unicam.cs.ids.municipeplatform.Report;

import java.util.List;

public interface ReportService {
    ReportEntity getReport(Long id);
    List<ReportEntity> getAllReports();
    ReportEntity createReport(ReportEntity report);
    void deleteReport(Long id);
}
