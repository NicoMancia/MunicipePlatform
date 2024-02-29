package it.unicam.cs.ids.municipeplatform.POI;
import it.unicam.cs.ids.municipeplatform.Content.ContentEntity;
import it.unicam.cs.ids.municipeplatform.DTOs.POICreationRequestDTO;
import it.unicam.cs.ids.municipeplatform.Location;
import jakarta.persistence.*;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class POIEntity extends ContentEntity
{
    private PoiCategory category;
    @Embedded
    private Location location;
    @Override
    public String getContentType() {
        return "POINT_OF_INTEREST";
    }

    public POIEntity(POICreationRequestDTO poiCreationRequestDTO) {
        super(
                poiCreationRequestDTO.getName(),
                poiCreationRequestDTO.getDescription(),
                poiCreationRequestDTO.getCreationDate(),
                poiCreationRequestDTO.getCreator()
        );

        this.category = poiCreationRequestDTO.getCategory();
        this.location = poiCreationRequestDTO.getLocation();
    }
}