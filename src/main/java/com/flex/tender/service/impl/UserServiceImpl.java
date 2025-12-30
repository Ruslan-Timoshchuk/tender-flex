package com.flex.tender.service.impl;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.flex.tender.model.User;
import com.flex.tender.repository.UserRepository;
import com.flex.tender.service.UserService;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.repository.GrantedAuthorityRoleRepository;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final GrantedAuthorityRoleRepository roleRepository;

    @Override
    public User findByEmail(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        user.setAuthorities(roleRepository.findByUser(user.getId()));
        return user;
    }
    
}