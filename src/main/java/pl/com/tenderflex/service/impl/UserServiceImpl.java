package pl.com.tenderflex.service.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pl.com.tenderflex.dao.UserRepository;
import pl.com.tenderflex.exception.ServiceException;
import pl.com.tenderflex.exception.UserNotFoundException;
import pl.com.tenderflex.model.User;
import pl.com.tenderflex.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getByEmail(String email) {
        try {
            return userRepository.getByEmail(email);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("The user with that email is not exists", e);
        } catch (DataAccessException e) {
            throw new ServiceException("Dao error occured", e);
        }
    }
}