package it.unicam.cs.ids.municipeplatform.TownHall;

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
    public TownHallEntity(String name, String description, Location location, double area) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.area = area;
    }
}
