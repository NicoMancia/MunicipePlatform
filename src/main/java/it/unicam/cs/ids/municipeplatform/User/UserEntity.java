package it.unicam.cs.ids.municipeplatform.User;
import it.unicam.cs.ids.municipeplatform.Notification.NotificationEntity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name="app_users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUtente;
    private String username;
    private String fullName;
    private String email;
    private Date registrationDate;
    private UserRole enumUser;

    @OneToMany
    private List<NotificationEntity> notificationList;
    public UserEntity(String username, String fullName, String email, Date registrationDate) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.registrationDate = registrationDate;

        this.notificationList = null;
    }

    public void addNotification(NotificationEntity n) {
        this.notificationList.add(n);
    }
}
