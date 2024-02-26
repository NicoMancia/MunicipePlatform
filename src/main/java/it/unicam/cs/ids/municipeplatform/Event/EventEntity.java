package it.unicam.cs.ids.municipeplatform.Event;

import it.unicam.cs.ids.municipeplatform.Content.ContentEntity;
import it.unicam.cs.ids.municipeplatform.Location;
import it.unicam.cs.ids.municipeplatform.DTOs.EventCreationRequestDTO;
import jakarta.persistence.Embedded;
import lombok.Getter;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class EventEntity extends ContentEntity {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @Embedded
    private Location location;
    @Override
    public String getContentType() {
        return "EVENT";
    }

    public EventEntity(EventCreationRequestDTO eventCreationRequestDTO) {
        super(
                eventCreationRequestDTO.getName(),
                eventCreationRequestDTO.getDescription(),
                eventCreationRequestDTO.getCreationDate(),
                eventCreationRequestDTO.getCreator()
        );

        this.startDate = eventCreationRequestDTO.getStartDate();
        this.endDate = eventCreationRequestDTO.getEndDate();
        this.location = eventCreationRequestDTO.getLocation();
    }
}
