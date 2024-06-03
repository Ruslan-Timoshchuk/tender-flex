package pl.com.tenderflex.security.impl;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.RoleRepository;
import pl.com.tenderflex.dao.UserRepository;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetailsImpl user = userRepository.getByEmail(email);
        user.setAuthorities(roleRepository.getByUser(user.getId()).stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name())).toList());
        return user;
    }
}