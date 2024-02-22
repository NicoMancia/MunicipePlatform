package it.unicam.cs.ids.municipeplatform.POI;
import it.unicam.cs.ids.municipeplatform.Content.ContentEntity;
import it.unicam.cs.ids.municipeplatform.DTOs.POICreationRequestDTO;
import it.unicam.cs.ids.municipeplatform.Location;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class POIEntity extends ContentEntity
{
    @Getter
    private PoiCategory category;
    @Getter
    @Embedded
    private Location location;

    /**
     * Retrieves the content type of the point of interest.
     *
     * @return The content type.
     */
    @Override
    public String getContentType() {
        return "POINT_OF_INTEREST";
    }

    /*
     * Constructs a PointOfInterest entity based on a {@link PointOfInterestCreationRequestDTO}.
     *
     * @param poiCreationRequestDTO The {@link PointOfInterestCreationRequestDTO} containing point of interest creation details.
     */
    public POIEntity(POICreationRequestDTO poiCreationRequestDTO) {
        super(
                poiCreationRequestDTO.getName(),
                poiCreationRequestDTO.getDescription(),
                poiCreationRequestDTO.getCreationDate(),
                poiCreationRequestDTO.getCreator()
        );

        this.category = poiCreationRequestDTO.getCategory();
        this.location = poiCreationRequestDTO.getLocation();

//        this.townHall = new TownHall();
//        this.townHall.setId(poiCreationRequestDTO.getTownHall());
    }
}