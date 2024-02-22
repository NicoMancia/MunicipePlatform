package it.unicam.cs.ids.municipeplatform.DTOs;

import it.unicam.cs.ids.municipeplatform.Location;
import it.unicam.cs.ids.municipeplatform.POI.PoiCategory;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class POICreationRequestDTO extends ContentCreationRequestDTO {
    private PoiCategory category;
    private Location location;
}