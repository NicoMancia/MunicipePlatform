package it.unicam.cs.ids.municipeplatform.DTOs;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ContentCreationRequestDTO {
    private String name;
    private String description;
    private LocalDateTime creationDate = LocalDateTime.now();
    private Long creator;
}
