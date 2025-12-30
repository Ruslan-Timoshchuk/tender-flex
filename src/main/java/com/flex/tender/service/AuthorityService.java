package com.flex.tender.service;

import java.util.List;

import com.flex.tender.model.Authority;
import com.flex.tender.model.User;
import com.flex.tender.model.enumeration.ERole;

public interface AuthorityService {

    public Authority getRole(ERole roleName);
    
    List<Authority> getAllByUser(Integer userId);

    boolean isContractor(User user);

    boolean isBidder(User user);
    
}