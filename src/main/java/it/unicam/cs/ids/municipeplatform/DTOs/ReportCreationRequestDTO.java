package it.unicam.cs.ids.municipeplatform.DTOs;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReportCreationRequestDTO {
    @Min(value = 0, message = "id must be a positive integer")
    private Long idCreator;

    @NotNull(message = "Description cannot be null")
    @Size(min = 2, max = 100, message = "Description must be between 2 and 100 characters")
    private String description;

    @NotNull(message = "Reporting date cannot be null")
    private LocalDateTime reportingDate = LocalDateTime.now();

    @Min(value = 0, message = "id must be a positive integer")
    private Long idContent;
}
