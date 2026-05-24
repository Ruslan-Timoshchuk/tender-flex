package com.flex.tender.service;

import com.flex.tender.model.User;

public interface UserService {

    User findByEmail(String email);

    User findById(Integer id);

}