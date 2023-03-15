package pl.com.tenderflex.security.impl;

import java.util.List;
import pl.com.tenderflex.model.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.RoleRepository;
import pl.com.tenderflex.dao.UserRepository;
import pl.com.tenderflex.model.User;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getByEmail(email);
        Integer userId = user.getId();
        List<Role> roles = roleRepository.getByUser(userId);
        return new UserDetailsImpl(userId, user.getEmail(), user.getPassword(),
                roles.stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).toList());
    }
}