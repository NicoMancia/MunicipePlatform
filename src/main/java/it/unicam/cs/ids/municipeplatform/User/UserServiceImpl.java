package it.unicam.cs.ids.municipeplatform.User;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public UserRole getRole(Long userId, Long id) {
//        List<UserEntity> roles = userRepository.findById(userId);
//
//        for (UserEntity i : roles) {
//            if (i.getIdUtente().equals(userId)) {
//                return i.getEnumUser();
//            }
//        }

        Optional<UserEntity> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            if (user.getIdUtente().equals(userId)) {
                return user.getEnumUser();
            }
        }
        throw new IllegalArgumentException("User does not have a role in the townhall");
    }


    @Override
    public void setRole(Long userId, Long id, UserRole role) {
//        List<UserEntity> roles = userRepository.findById(userId);
//
//        for (UserEntity i : roles) {
//            if (i.getIdUtente().equals(userId)) {
//               /* // throw if the townHall does not exist
//                townHallService.getById(townHallId);*/
//
//                i.setEnumUser(role);
//                userRepository.save(i);
//                return;
//            }
//        }
        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            user.setEnumUser(role);
            userRepository.save(user);
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
