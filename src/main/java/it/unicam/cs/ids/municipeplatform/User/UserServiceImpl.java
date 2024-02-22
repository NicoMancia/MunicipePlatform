package it.unicam.cs.ids.municipeplatform.User;

import it.unicam.cs.ids.municipeplatform.TownHall.*;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    /*private final TownHallRepository townHallRepository;
    private final TownHallService townHallService;*/

    public UserServiceImpl(UserRepository userRepository/*, TownHallRepository townHallRepository, TownHallService townHallService*/) {
        this.userRepository = userRepository;
       /* this.townHallRepository = townHallRepository;
        this.townHallService = townHallService;*/
    }

    /**
     * Creates a new user with a specified role in a town hall. Validates the existence of the town hall and the validity of the role.
     * Throws IllegalArgumentException if the town hall does not exist, the role is invalid, or the user already exists.
     *
     * @param user The user to be created.
     * @param townHall The ID of the town hall where the user will have a role.
     * @param role The index of the role from the Role enum.
     * @return The created user entity.
     * @throws IllegalArgumentException if town hall does not exist, role is invalid, or user already exists.
     */
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

    /**
     * Retrieves the role of a user in a specific town hall. Validates the existence of the town hall.
     * Throws IllegalArgumentException if the user does not have a role in the specified town hall.
     *
     * @param userId The ID of the user whose role is to be retrieved.
     * @param townHallId The ID of the town hall.
     * @return The Role of the user in the specified town hall.
     * @throws IllegalArgumentException if the user does not have a role in the specified town hall.
     */
    @Override
    public UserRole getRole(Long userId) {
        List<UserEntity> roles = userRepository.findById(userId);

        for (UserEntity i : roles) {
            if (i.getIdUtente().equals(userId)) {
                return i.getEnumUser();
            }
        }

        throw new IllegalArgumentException("User does not have a role in the townhall");
    }

    /**
     * Sets a new role for a user in a specific town hall. Validates the existence of the town hall and the user's role.
     * Updates the user's role if it exists, otherwise throws IllegalArgumentException.
     *
     * @param userId The ID of the user whose role is to be set.
     * @param townHallId The ID of the town hall where the role is to be set.
     * @param role The new role to be assigned to the user.
     * @throws IllegalArgumentException if the user does not have a role in the specified town hall.
     */
    @Override
    public void setRole(Long userId, UserRole role) {
        List<UserEntity> roles = userRepository.findById(userId);

        for (UserEntity i : roles) {
            if (i.getIdUtente().equals(userId)) {
               /* // throw if the townHall does not exist
                townHallService.getById(townHallId);*/

                i.setEnumUser(role);
                userRepository.save(i);
                return;
            }
        }

        throw new IllegalArgumentException("User does not have a role in this townHall");
    }
    /**
     * Retrieves all users available in the system.
     *
     * @return A list of all users.
     */
    @Override
    public List<UserEntity> findAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Deletes a user by their ID. Validates the existence of the user before deletion.
     *
     * @param id The ID of the user to be deleted.
     * @throws IllegalArgumentException if the user does not exist.
     */
    @Override
    public void delete(Long id) {
        if(!userRepository.existsById(id)) {
            throw new IllegalArgumentException("| ERROR | ID does not exist");
        }

        userRepository.delete(userRepository.findById(id).get());
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The retrieved user entity.
     */
    @Override
    public UserEntity getUser(Long id) {
        return userRepository.findById(id).orElseThrow();
    }
}
