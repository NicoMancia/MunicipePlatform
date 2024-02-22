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
    private int difficultyLevel;
    @ManyToMany
    private List<ContentEntity> contents;
    /**
     * Retrieves the content type of the itinerary.
     *
     * @return The content type.
     */
    @Override
    public String getContentType() {
        return "ITINERARY";
    }

    /*
     * Constructs an Itinerary entity based on an {@link ItineraryCreationRequestDTO}.
     *
     * @param itineraryCreationRequestDTO The {@link ItineraryCreationRequestDTO} containing itinerary creation details.
     */
    public ItineraryEntity(ItineraryCreationRequestDTO itineraryCreationRequestDTO) {
        super(
                itineraryCreationRequestDTO.getName(),
                itineraryCreationRequestDTO.getDescription(),
                itineraryCreationRequestDTO.getCreationDate(),
                itineraryCreationRequestDTO.getCreator()
        );


        // this.difficultyLevel = itineraryCreationRequestDTO.getDifficultyLevel();
        this.contents = new ArrayList<>();
        //this.townHall = new TownHallEntity();
        //this.townHall.setId(itineraryCreationRequestDTO.getTownHall());
    }
}

