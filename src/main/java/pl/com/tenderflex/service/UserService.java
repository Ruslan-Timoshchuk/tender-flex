package pl.com.tenderflex.service;

import pl.com.tenderflex.model.User;

public interface UserService {

    User getByEmail(String email);
    
}