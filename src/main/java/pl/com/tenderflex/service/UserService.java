package pl.com.tenderflex.service;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pl.com.tenderflex.dao.UserDao;
import pl.com.tenderflex.exception.ServiceException;
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
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("The user with that email is not exists", e);
        } catch (DataAccessException e) {
            throw new ServiceException("Dao error occured", e);
        }
    }
}