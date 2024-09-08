package vn.edu.fptu.prm392.users;

import jakarta.annotation.Nonnull;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import vn.edu.fptu.prm392.users.securities.SecurityUser;

import java.util.Objects;
import java.util.Optional;


@Configuration
public class SpringAuditorAware implements AuditorAware<String> {

    @Nonnull
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(getSecurityUser(SecurityContextHolder.getContext().getAuthentication()));
    }

    private String getSecurityUser(Authentication authentication) {
        if (Objects.nonNull(authentication) && authentication.isAuthenticated() && authentication.getPrincipal() instanceof SecurityUser securityUser) {
            return securityUser.getUsername();
        }
        return "unknown";
    }
}
