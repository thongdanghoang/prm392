package vn.edu.fptu.prm392.users.securities;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.fptu.prm392.users.entities.AuthorityEntity;
import vn.edu.fptu.prm392.users.entities.UserEntity;
import vn.edu.fptu.prm392.users.enums.UserRole;
import vn.edu.fptu.prm392.users.repositories.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userDetail = repository.findByUsernameWithAuthorities(username);

        // Converting UserInfo to UserDetails
        return userDetail
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public void addUser(UserEntity userEntity) {
        // Encode password before saving the user
        userEntity.setPassword(encoder.encode(userEntity.getPassword()));
        repository.save(userEntity);
    }

    public Optional<AuthorityEntity> findAuthority(UserRole role) {
        return repository.findUserByRole(role.toString());
    }

}

