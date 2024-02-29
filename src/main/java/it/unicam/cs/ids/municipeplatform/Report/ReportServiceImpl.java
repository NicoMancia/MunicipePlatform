package it.unicam.cs.ids.municipeplatform.Report;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ReportServiceImpl implements ReportService{
    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public ReportEntity getReport(Long id) {
        return this.reportRepository.findReportById(id);
    }

    @Override
    public List<ReportEntity> getAllReports() {
        return StreamSupport.stream(reportRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public ReportEntity createReport(ReportEntity report) {
        report.setEnumReport(StateReport.PENDING);
        ReportEntity newReport= reportRepository.save(report);
        return newReport;
    }


    @Override
    public void deleteReport(Long id) {
        if(!reportRepository.existsById(id)) {
            throw new IllegalArgumentException("| ERROR | ID does not exist");
        }

        reportRepository.delete(reportRepository.findById(id).get());
    }
}
