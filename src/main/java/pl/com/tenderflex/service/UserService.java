package pl.com.tenderflex.service;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import pl.com.tenderflex.dao.UserDao;
import pl.com.tenderflex.exception.UserNotFoundException;
import pl.com.tenderflex.model.User;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getByEmail(String email) {
        try {
            return userDao.getByEmail(email);
        } catch (DataAccessException e) {
            throw new UserNotFoundException("Error occurred when searching by user's email", e);
        }
    }
}