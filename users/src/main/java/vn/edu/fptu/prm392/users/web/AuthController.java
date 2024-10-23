package vn.edu.fptu.prm392.users.web;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fptu.prm392.users.dtos.AuthRequest;
import vn.edu.fptu.prm392.users.dtos.TokenResponse;
import vn.edu.fptu.prm392.users.entities.AuthorityEntity;
import vn.edu.fptu.prm392.users.entities.UserEntity;
import vn.edu.fptu.prm392.users.repositories.UserRepository;
import vn.edu.fptu.prm392.users.securities.jwt.JwtService;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @PostMapping("/sign-in")
    public TokenResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password())
        );
        Optional<UserEntity> userEntity = userRepository
                .findByUsernameWithAuthorities(authRequest.username());
        if (authentication.isAuthenticated() && userEntity.isPresent()) {
            UserEntity user = userEntity.get();
            Set<String> roles = user
                    .getAuthorities()
                    .stream()
                    .map(AuthorityEntity::getRole)
                    .collect(Collectors.toSet());
            return new TokenResponse(jwtService.generateToken(user.getUsername(), roles, user.getId()));
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}


