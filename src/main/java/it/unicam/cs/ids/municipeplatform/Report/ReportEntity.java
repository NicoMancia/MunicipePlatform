package it.unicam.cs.ids.municipeplatform.Report;

import java.time.LocalDateTime;
import java.util.Date;

import it.unicam.cs.ids.municipeplatform.Content.ContentEntity;
import it.unicam.cs.ids.municipeplatform.DTOs.ReportCreationRequestDTO;
import it.unicam.cs.ids.municipeplatform.DTOs.UserCreationRequestDTO;
import it.unicam.cs.ids.municipeplatform.User.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.Date;
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
    StateReport enumReport;
    @ManyToOne
    private ContentEntity content;

    public ReportEntity(ReportCreationRequestDTO reportCreationRequestDTO) {
        this.reporter = new UserEntity();
        this.description = reportCreationRequestDTO.getDescription();
        this.reportingDate = reportCreationRequestDTO.getReportingDate();
        //this.content= new ContentEntity();

    }
}