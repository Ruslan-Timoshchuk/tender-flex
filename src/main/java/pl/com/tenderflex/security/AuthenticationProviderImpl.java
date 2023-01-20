package pl.com.tenderflex.security;

import java.util.Collections;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.com.tenderflex.exception.AuthenticationProviderException;
import pl.com.tenderflex.exception.UserNotFoundException;
import pl.com.tenderflex.model.User;
import pl.com.tenderflex.service.UserService;

@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationProviderImpl(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        try {
            User userDetails = userService.getByEmail(authentication.getName());
            String password = authentication.getCredentials().toString();
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("Incorrect password");
            } else {
                return new UsernamePasswordAuthenticationToken(userDetails, password,
                        Collections.singleton(new SimpleGrantedAuthority(String.valueOf(userDetails.getRole()))));
            }
        } catch (BadCredentialsException | UserNotFoundException e) {
            throw new AuthenticationProviderException("Incorrect email or password", e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}