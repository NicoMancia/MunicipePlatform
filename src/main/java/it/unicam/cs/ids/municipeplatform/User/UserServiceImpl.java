package it.unicam.cs.ids.municipeplatform.User;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public UserEntity createUser(UserEntity user, int role) {

        if (role < 0 || role > UserRole.values().length) {
            throw new IllegalArgumentException("| ERROR | Role does not exist");
        }

        if (userRepository.exists(user.getUsername(), user.getEmail())) {
            throw new IllegalArgumentException("| ERROR | User already exists");
        }

        user.setEnumUser(UserRole.values()[role]);

        UserEntity newUser = userRepository.save(user);

        return newUser;
    }

    @Override
    public UserRole getRole(Long userId) {
        List<UserEntity> roles = userRepository.findTownHallRolesByUserId(userId);

        for (UserEntity i : roles) {
            return i.getEnumUser();
        }
        throw new IllegalArgumentException("User does not have a role in this townhall");
    }


    @Override
    public void setRole(Long userId, UserRole role) {
        List<UserEntity> roles = userRepository.findTownHallRolesByUserId(userId);

        for (UserEntity i : roles) {
            i.setEnumUser(role);
            userRepository.save(i);
            return;
        }

        throw new IllegalArgumentException("User does not have a role in this townHall");
    }
    @Override
    public List<UserEntity> findAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }


    @Override
    public void delete(Long id) {
        if(!userRepository.existsById(id)) {
            throw new IllegalArgumentException("| ERROR | ID does not exist");
        }

        userRepository.delete(userRepository.findById(id).get());
    }

    @Override
    public UserEntity getUser(Long id) {
        return userRepository.findById(id).orElseThrow();
    }
}
