package pl.com.tenderflex.service.impl;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.UserRepository;
import pl.com.tenderflex.model.User;
import pl.com.tenderflex.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }
}