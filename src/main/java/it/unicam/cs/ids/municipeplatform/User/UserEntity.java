package it.unicam.cs.ids.municipeplatform.User;
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
    private UserType enumUser;
    public UserEntity(String username, String fullName, String email, Date registrationDate) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.registrationDate = registrationDate;
    }

    public void setIdUser(Long idUtente) {
        this.idUtente = idUtente;
    }

    public UserType getEnumUser() {
        return enumUser;
    }

    public void setEnumUser(UserType enumUser) {
        this.enumUser = enumUser;
    }

    public Long getIdUtente() {
        return idUtente;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
}
