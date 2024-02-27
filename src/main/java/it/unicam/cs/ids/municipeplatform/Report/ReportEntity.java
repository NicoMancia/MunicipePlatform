package it.unicam.cs.ids.municipeplatform.Report;

import java.util.Date;

import it.unicam.cs.ids.municipeplatform.Content.ContentEntity;
import it.unicam.cs.ids.municipeplatform.User.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name="report")
public class ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="report_id_seq")
    private Long Id;
    @ManyToOne
    private UserEntity reporter;
    private String description;
    private Date reportingDate;
    private int status;
    private String resolution;
    @ManyToOne
    private ContentEntity content;
}