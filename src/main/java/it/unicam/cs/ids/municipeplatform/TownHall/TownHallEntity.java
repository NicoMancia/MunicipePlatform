package it.unicam.cs.ids.municipeplatform.TownHall;

import it.unicam.cs.ids.municipeplatform.DTOs.TownHallCreationRequestDTO;
import it.unicam.cs.ids.municipeplatform.Location;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name="townhall")
public class TownHallEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="townHall_id_seq")
    private Long Id;
    private String name;
    private String description;
    @Embedded
    private Location location;
    private double area;
    public TownHallEntity(TownHallCreationRequestDTO dto) {
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.location = dto.getLocation();
        this.area = dto.getArea();
    }
}
