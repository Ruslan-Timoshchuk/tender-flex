package pl.com.tenderflex.dao;

import pl.com.tenderflex.security.impl.UserDetailsImpl;

public interface UserRepository {

    UserDetailsImpl getByEmail(String email);
    
}