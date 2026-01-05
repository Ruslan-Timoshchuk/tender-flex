package com.flex.tender.repository;

import java.util.Optional;
import com.flex.tender.model.User;

public interface UserRepository {

    Optional<User> findByEmail(String email);
    
}