package com.flex.tender.service.read.impl;

import static java.lang.String.format;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.flex.tender.model.User;
import com.flex.tender.repository.UserRepository;
import com.flex.tender.service.read.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    public static final String LOG_MSG_ON_USER_RETRIEVAL_FAILED = "User lookup failed for email = {}: the user is not found";

    private final UserRepository userRepository;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> {
            log.warn(LOG_MSG_ON_USER_RETRIEVAL_FAILED, email);
            return new UsernameNotFoundException(format("User with email = %s is not found.", email));
        });
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id);
    }

}