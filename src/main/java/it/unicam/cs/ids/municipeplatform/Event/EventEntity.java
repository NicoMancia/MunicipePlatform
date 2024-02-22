package it.unicam.cs.ids.municipeplatform.Event;

import it.unicam.cs.ids.municipeplatform.Content.ContentEntity;
import it.unicam.cs.ids.municipeplatform.Location;
import it.unicam.cs.ids.municipeplatform.TownHall.TownHallEntity;
import jakarta.persistence.Embedded;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class EventEntity extends ContentEntity {
    @Getter
    private LocalDateTime startDate;
    @Getter
    private LocalDateTime endDate;
    @Getter
    @Embedded
    private Location location;
    @Override
    public String getContentType() {
        return "EVENT";
    }

    public EventEntity(String name, String description, Date creationDate, Long creator, LocalDateTime startDate, LocalDateTime endDate, Location location) {
        super(name, description, creationDate, creator);
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
    }

   /* public EventEntity(EventCreationRequestDTO eventCreationRequestDTO) {
        super(
                eventCreationRequestDTO.getName(),
                eventCreationRequestDTO.getDescription(),
                eventCreationRequestDTO.getCreationDate(),
                eventCreationRequestDTO.getCreator()
        );

        this.startDate = eventCreationRequestDTO.getStartDate();
        this.endDate = eventCreationRequestDTO.getEndDate();
        this.location = eventCreationRequestDTO.getLocation();
        this.townHall = new TownHallEntity();
        this.townHall.setId(eventCreationRequestDTO.getTownHall());
    }*/
}
