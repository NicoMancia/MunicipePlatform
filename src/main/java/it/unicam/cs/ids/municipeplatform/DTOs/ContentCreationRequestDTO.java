package it.unicam.cs.ids.municipeplatform.DTOs;

import lombok.*;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ContentCreationRequestDTO {
    private String name;
    private String description;
    private Date creationDate;
    private Long creator;
}
