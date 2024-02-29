package it.unicam.cs.ids.municipeplatform.User;

import it.unicam.cs.ids.municipeplatform.DataManagerController;
import it.unicam.cs.ids.municipeplatform.DTOs.UserCreationRequestDTO;
import it.unicam.cs.ids.municipeplatform.DTOs.UserRoleChangeDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController implements DataManagerController<UserCreationRequestDTO, Long> {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/role/")
    public ResponseEntity<?> changeRole(@RequestBody UserRoleChangeDTO dto) {
        try {
            if (userService.getRole(dto.getUserId()) == dto.getNewRole()) {
                return ResponseEntity.badRequest().body("User already has this role.");
            }

            userService.setRole(dto.getUserId(), dto.getNewRole());

            return ResponseEntity.ok().body("Role change success.");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> create(UserCreationRequestDTO dto) {
        UserEntity newUser = userService.createUser(new UserEntity(dto),
                dto.getRole());
        return ResponseEntity.ok(newUser);
    }

    @Override
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @Override
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @Override
    public ResponseEntity<?> update(Long aLong, UserCreationRequestDTO entity) {
        // Bad request. No update use case for users yet.
        return ResponseEntity.badRequest().body("Not implemented.");
    }

    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.ok().body("User successfully deleted.");
    }
}

