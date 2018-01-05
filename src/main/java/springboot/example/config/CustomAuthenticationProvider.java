package springboot.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import springboot.example.model.User;
import springboot.example.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final Optional<User> userOptional = findUser(authentication);
        final User user = userOptional.orElseThrow(() ->
                new BadCredentialsException("Incorrect e-mail address or password."));

        if (!matchPassword(authentication, user)) {
            throw new BadCredentialsException("Incorrect e-mail address or password.");
        }

        return buildAuthenticationToken(user);
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return true;
    }

    private Optional<User> findUser(final Authentication authentication) {
        return userRepository.findByEmail(authentication.getName());
    }

    private boolean matchPassword(Authentication authentication, User user) {
        final String password = (String) authentication.getCredentials();
        return passwordEncoder.matches(password, user.getPassword());
    }

    private UserDetails buildUserDetails(User user, Collection<? extends GrantedAuthority> authorities) {
        return new AuthenticatedUserDetails(user.getEmail(),
                user.getPassword(),
                authorities,
                user.getName());
    }

    private Authentication buildAuthenticationToken(User user) {
        final List<GrantedAuthority> authorities = createAuthorityList("ROLE_USER");
        return new UsernamePasswordAuthenticationToken(buildUserDetails(user, authorities), user.getPassword(),
                authorities);
    }
}