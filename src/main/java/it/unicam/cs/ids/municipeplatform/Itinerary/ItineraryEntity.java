package it.unicam.cs.ids.municipeplatform.Itinerary;
import it.unicam.cs.ids.municipeplatform.Content.ContentEntity;
import it.unicam.cs.ids.municipeplatform.DTOs.ItineraryCreationRequestDTO;
import it.unicam.cs.ids.municipeplatform.TownHall.TownHallEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class ItineraryEntity extends ContentEntity {

    @Getter
    private LocalDateTime endDate;

    @ManyToMany
    private List<ContentEntity> contents;
    @Override
    public String getContentType() {
        return "ITINERARY";
    }

    public ItineraryEntity(ItineraryCreationRequestDTO itineraryCreationRequestDTO) {
        super(
                itineraryCreationRequestDTO.getName(),
                itineraryCreationRequestDTO.getDescription(),
                itineraryCreationRequestDTO.getCreationDate(),
                itineraryCreationRequestDTO.getCreator()
        );

        this.contents = new ArrayList<>();
        this.endDate = itineraryCreationRequestDTO.getEndItineraryDate();
    }
}

