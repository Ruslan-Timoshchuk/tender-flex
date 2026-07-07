package com.flex.tender.service.read;

import com.flex.tender.model.User;

public interface CustomUserDetailsService {

    User findByEmail(String email);

    User findById(Integer id);

}