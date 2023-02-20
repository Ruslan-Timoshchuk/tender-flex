package pl.com.tenderflex.service.impl;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.UserDao;
import pl.com.tenderflex.model.User;
import pl.com.tenderflex.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public User getByEmail(String email) {
        return userDao.getByEmail(email);
    }
}