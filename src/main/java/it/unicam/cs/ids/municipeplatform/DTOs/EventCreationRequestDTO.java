package it.unicam.cs.ids.municipeplatform.DTOs;

import it.unicam.cs.ids.municipeplatform.Event.EventCategory;
import it.unicam.cs.ids.municipeplatform.Location;
import lombok.*;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventCreationRequestDTO extends ContentCreationRequestDTO {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private EventCategory category;
    private Location location;
}