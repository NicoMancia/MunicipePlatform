package it.unicam.cs.ids.municipeplatform.DTOs;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItineraryCreationRequestDTO extends ContentCreationRequestDTO {
    private LocalDateTime estimatedDuration;
    private List<Long> contents;
}