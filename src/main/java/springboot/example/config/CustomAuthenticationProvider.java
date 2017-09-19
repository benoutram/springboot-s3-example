package springboot.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import springboot.example.model.User;
import springboot.example.repository.UserRepository;

import java.util.Optional;

import static java.util.Collections.emptyList;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final Optional<User> userOptional = userRepository.findByEmail(authentication.getName());
        final User user = userOptional.orElseThrow(() -> new BadCredentialsException("Incorrect e-mail address or password."));

        final String password = (String) authentication.getCredentials();
        if (!password.equals(user.getPassword())) {
            throw new BadCredentialsException("Incorrect e-mail address or password.");
        }

        return new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), emptyList());
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return true;
    }
}