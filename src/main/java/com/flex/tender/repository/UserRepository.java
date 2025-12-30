package com.flex.tender.repository;

import com.flex.tender.model.User;

public interface UserRepository {

    User findByEmail(String email);
    
}