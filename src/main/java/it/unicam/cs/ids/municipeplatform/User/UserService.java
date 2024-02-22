package it.unicam.cs.ids.municipeplatform.User;

import java.util.List;

public interface UserService {
    UserEntity createUser(UserEntity user, int role);
    //void updateSuspensionStatus(Long id, boolean newStatus);
    List<UserEntity> findAll();
    void delete(Long id);
    UserEntity getUser(Long id);
    UserRole getRole(Long userId, Long id);
    void setRole(Long userId, Long id, UserRole role);
}
