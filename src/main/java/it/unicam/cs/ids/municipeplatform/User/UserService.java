package it.unicam.cs.ids.municipeplatform.User;

import java.util.List;

public interface UserService {
    UserEntity createUser(UserEntity user, int role);
    List<UserEntity> findAll();
    void delete(Long id);
    UserEntity getUser(Long id);
    UserRole getRole(Long userId);
    void setRole(Long userId, UserRole role);
}