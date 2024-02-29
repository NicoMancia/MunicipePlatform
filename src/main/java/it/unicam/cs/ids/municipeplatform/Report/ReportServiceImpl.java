package it.unicam.cs.ids.municipeplatform.Report;

import it.unicam.cs.ids.municipeplatform.Content.ContentEntity;
import it.unicam.cs.ids.municipeplatform.Content.ContentRepository;
import it.unicam.cs.ids.municipeplatform.User.UserEntity;
import it.unicam.cs.ids.municipeplatform.User.UserRepository;
import it.unicam.cs.ids.municipeplatform.User.UserRole;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service

public class ReportServiceImpl implements ReportService{
    private final ReportRepository reportRepository;
    private final ContentRepository contentRepository;
    private final UserRepository userRepository;

    public ReportServiceImpl(ReportRepository reportRepository, ContentRepository contentRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.contentRepository = contentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ReportEntity getReportById(Long id) {
        Optional<ReportEntity> optionalContent = reportRepository.findById(id);
        if (optionalContent.isEmpty()) {
            throw new EntityNotFoundException("Report doesn't exist.");
        }
        return this.reportRepository.findReportById(id);
    }

    @Override
    public List<ReportEntity> getAllPendingReports()
    {
        return new ArrayList<>(reportRepository.findAllPending());
    }

    @Override
    public List<ReportEntity> getAllReportsByIdEvent(Long contentId) {
        Optional<ContentEntity> optionalContent = contentRepository.findById(contentId);
        if (optionalContent.isEmpty()) {
            throw new EntityNotFoundException("Content doesn't exist.");
        }
        return reportRepository.findReportsByEventId(contentId);
    }
    @Override
    public List<ReportEntity> getAllReportsByIdPOI(Long contentId) {
        Optional<ContentEntity> optionalContent = contentRepository.findById(contentId);
        if (optionalContent.isEmpty()) {
            throw new EntityNotFoundException("Content doesn't exist.");
        }
        return reportRepository.findReportsByPOIId(contentId);
    }
    @Override
    public List<ReportEntity> getAllReportsByIdItinerary(Long contentId) {
        Optional<ContentEntity> optionalContent = contentRepository.findById(contentId);
        if (optionalContent.isEmpty()) {
            throw new EntityNotFoundException("Content doesn't exist.");
        }
        return reportRepository.findReportsByItineraryId(contentId);
    }
    @Override
    public ReportEntity createReport(ReportEntity report) {
        if (report == null) {
            throw new IllegalArgumentException("Report is NULL.");
        }
        report.setReporter(userRepository.findById(report.getReporter().getIdUser())
                .orElseThrow(() -> new IllegalArgumentException("Reporter doesn't exist.")));

        UserEntity reporter = userRepository.findById(report.getReporter().getIdUser()).stream().findFirst().orElseThrow();

        if (reporter.getEnumUser() == UserRole.AUTHENTICATED_TOURIST) {
            report.setStatus(StateReport.PENDING);
            return reportRepository.save(report);
        }
        else throw new IllegalArgumentException("Only authorized users can create a report.");
    }
    @Override
    public void deleteReport(Long id) {
        if(!reportRepository.existsById(id)) {
            throw new IllegalArgumentException("ID does not exist.");
        }
        reportRepository.delete(reportRepository.findById(id).get());
    }
}
