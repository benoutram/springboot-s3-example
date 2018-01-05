package springboot.example.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AuthenticatedUserDetails extends User {

    private String name;

    public AuthenticatedUserDetails(final String username, final String password, final Collection<? extends
            GrantedAuthority> authorities, final String name) {
        super(username, password, authorities);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
