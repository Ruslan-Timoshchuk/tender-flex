package com.flex.tender.service.impl;

import static java.lang.String.format;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.flex.tender.model.User;
import com.flex.tender.repository.UserRepository;
import com.flex.tender.service.UserService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(format("User with email = %s is not found.", email)));
    }

}