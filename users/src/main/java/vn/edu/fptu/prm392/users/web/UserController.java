package vn.edu.fptu.prm392.users.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.fptu.prm392.users.dtos.RegisterRequest;
import vn.edu.fptu.prm392.users.entities.UserEntity;
import vn.edu.fptu.prm392.users.enums.UserRole;
import vn.edu.fptu.prm392.users.securities.UserSecurityService;

import java.util.Set;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserSecurityService service;

    @GetMapping("/profile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile() {
        return "Welcome to User Profile";
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Void> addNewUser(@RequestBody RegisterRequest user) {
        UserEntity register = UserEntity.register(user.username(), user.password());
        var userRole = service.findAuthority(UserRole.ROLE_USER);
        if(userRole.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        register.setAuthorities(Set.of(userRole.get()));
        service.addUser(register);
        return ResponseEntity.ok().build();
    }

}
