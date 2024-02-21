package it.unicam.cs.ids.municipeplatform;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Embeddable
public class Location
{
    private float longitude;
    private float latitude;
}
