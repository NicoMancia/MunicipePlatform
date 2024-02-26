package it.unicam.cs.ids.municipeplatform.DTOs;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItineraryCreationRequestDTO extends ContentCreationRequestDTO {
    private List<Long> contents;
    private Date endItineraryDate;
}