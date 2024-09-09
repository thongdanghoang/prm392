package vn.edu.fptu.prm392.users.securities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.edu.fptu.prm392.users.entities.AuthorityEntity;
import vn.edu.fptu.prm392.users.entities.UserEntity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SecurityUser implements UserDetails {

    private final String username;
    private final String password;
    private final List<GrantedAuthority> authorities;

    public SecurityUser(UserEntity userEntity) {
        this.username = userEntity.getUsername(); // Assuming 'name' is used as 'username'
        this.password = userEntity.getPassword();
        this.authorities = userEntity.getAuthorities()
                .stream()
                .map(this::toGrantedAuthority)
                .collect(Collectors.toList());
    }

    private SimpleGrantedAuthority toGrantedAuthority(AuthorityEntity authority) {
        return new SimpleGrantedAuthority(authority.getRole());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Implement your logic if you need this
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Implement your logic if you need this
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Implement your logic if you need this
    }

    @Override
    public boolean isEnabled() {
        return true; // Implement your logic if you need this
    }
}

