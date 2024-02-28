package it.unicam.cs.ids.municipeplatform.Notification;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name="Notification")
public class NotificationEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="notification_id_seq")
    private Long Id;
    private String title;
    private String description;
    private LocalDateTime timeStamp;

    public NotificationEntity(String title, String description)
    {
        if (title.isBlank() || description.isBlank())
        {
            throw new IllegalArgumentException("| ERROR | Title or Description are blank :(");
        }

        this.title = title;
        this.description = description;
        this.timeStamp = LocalDateTime.now();
    }
}

