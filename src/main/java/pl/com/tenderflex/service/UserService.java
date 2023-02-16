package pl.com.tenderflex.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.UserDao;
import pl.com.tenderflex.model.User;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    public User getByEmail(String email) {
        return userDao.getByEmail(email);
    }
}