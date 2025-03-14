package pl.com.tenderflex.repository;

import pl.com.tenderflex.model.User;

public interface UserRepository {

    User findByEmail(String email);
    
}