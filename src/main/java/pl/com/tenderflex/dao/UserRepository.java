package pl.com.tenderflex.dao;

import pl.com.tenderflex.model.User;

public interface UserRepository {

    User getByEmail(String email);
    
}