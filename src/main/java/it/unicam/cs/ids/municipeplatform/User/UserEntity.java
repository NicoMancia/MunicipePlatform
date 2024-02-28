package it.unicam.cs.ids.municipeplatform.User;
import it.unicam.cs.ids.municipeplatform.Notification.NotificationEntity;
import it.unicam.cs.ids.municipeplatform.DTOs.UserCreationRequestDTO;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name="UserEntity")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    private String username;
    private String fullName;
    private String email;
    private LocalDateTime registrationDate;
    private UserRole enumUser;

    @OneToMany
    private List<NotificationEntity> notificationList;
    public UserEntity(UserCreationRequestDTO userCreationRequestDTO) {
        this.username = userCreationRequestDTO.getUsername();
        this.fullName = userCreationRequestDTO.getFullName();
        this.email = userCreationRequestDTO.getEmail();
        this.registrationDate = userCreationRequestDTO.getRegistrationDate();
        this.notificationList = null;
    }

    public void addNotification(NotificationEntity n) {
        this.notificationList.add(n);
    }
}
