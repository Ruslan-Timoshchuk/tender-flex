package com.flex.tender.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.flex.tender.model.User;

public interface UserService {

    User findByEmail(String email) throws UsernameNotFoundException;

}
