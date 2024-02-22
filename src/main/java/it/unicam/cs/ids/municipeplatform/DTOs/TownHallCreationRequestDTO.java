package it.unicam.cs.ids.municipeplatform.DTOs;
import it.unicam.cs.ids.municipeplatform.Location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TownHallCreationRequestDTO {
    private String name;
    private String description;
    private Location location;
    private double area;
}
