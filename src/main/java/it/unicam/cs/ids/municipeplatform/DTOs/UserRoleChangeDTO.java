package it.unicam.cs.ids.municipeplatform.DTOs;

import it.unicam.cs.ids.municipeplatform.User.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRoleChangeDTO extends UserBasicDTO {
    UserRole newRole;
}
