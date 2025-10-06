package vn.fss.bps.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class ServerAuthProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String isAuthenticated = authentication.getCredentials().toString();

        if (!"Y".equals(isAuthenticated))
            throw new BadCredentialsException("Invalid API key");

        return new AuthToken(null, isAuthenticated);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AuthToken.class.isAssignableFrom(authentication);
    }

    public static class AuthToken extends AbstractAuthenticationToken {

        private final String isAuthenticated;

        public AuthToken(Collection<? extends GrantedAuthority> authorities, String isAuthenticated) {
            super(authorities);
            this.isAuthenticated = isAuthenticated;
            setAuthenticated(true);
        }

        public AuthToken(String isAuthenticated) {
            super(null);
            this.isAuthenticated = isAuthenticated;
            setAuthenticated(false);
        }

        @Override
        public Object getCredentials() {
            return isAuthenticated;
        }

        @Override
        public Object getPrincipal() {
            return null;
        }
    }
}
