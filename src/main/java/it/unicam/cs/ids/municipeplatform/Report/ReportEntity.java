package it.unicam.cs.ids.municipeplatform.Report;

import java.time.LocalDateTime;

import it.unicam.cs.ids.municipeplatform.DTOs.ReportEventCreationRequestDTO;
import it.unicam.cs.ids.municipeplatform.DTOs.ReportItineraryCreationRequestDTO;
import it.unicam.cs.ids.municipeplatform.DTOs.ReportPOICreationRequestDTO;
import it.unicam.cs.ids.municipeplatform.Event.EventEntity;
import it.unicam.cs.ids.municipeplatform.Itinerary.ItineraryEntity;
import it.unicam.cs.ids.municipeplatform.POI.POIEntity;
import it.unicam.cs.ids.municipeplatform.User.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name="Report")
public class ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="report_id_seq")
    private Long Id;
    @ManyToOne
    private UserEntity reporter;
    private String description;
    private LocalDateTime reportingDate;
    StateReport status;
    @ManyToOne
    private POIEntity poiEntity = null;
    @ManyToOne
    private EventEntity eventEntity = null;
    @ManyToOne
    private ItineraryEntity itineraryEntity = null;


    public ReportEntity(ReportEventCreationRequestDTO reportEventCreationRequestDTO) {
        this.description = reportEventCreationRequestDTO.getDescription();
        this.reportingDate = reportEventCreationRequestDTO.getReportingDate();
        this.status = StateReport.PENDING;

        this.reporter = new UserEntity();
        reporter.setIdUser(reportEventCreationRequestDTO.getReporter());

        this.eventEntity = new EventEntity();
        eventEntity.setId(reportEventCreationRequestDTO.getIdEvent());
    }

    public ReportEntity(ReportItineraryCreationRequestDTO reportItineraryCreationRequestDTO) {
        this.description = reportItineraryCreationRequestDTO.getDescription();
        this.reportingDate = reportItineraryCreationRequestDTO.getReportingDate();
        this.status = reportItineraryCreationRequestDTO.getStatus();

        this.reporter = new UserEntity();
        reporter.setIdUser(reportItineraryCreationRequestDTO.getReporter());

        this.itineraryEntity = new ItineraryEntity();
        itineraryEntity.setId(reportItineraryCreationRequestDTO.getIdItinerary());
    }
    public ReportEntity(ReportPOICreationRequestDTO reportPOICreationRequestDTO) {
        this.description = reportPOICreationRequestDTO.getDescription();
        this.reportingDate = reportPOICreationRequestDTO.getReportingDate();
        this.status = reportPOICreationRequestDTO.getStatus();

        this.reporter = new UserEntity();
        reporter.setIdUser(reportPOICreationRequestDTO.getReporter());

        this.poiEntity = new POIEntity();
        poiEntity.setId(reportPOICreationRequestDTO.getIdPOI());
    }
}