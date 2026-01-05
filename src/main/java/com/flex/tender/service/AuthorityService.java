package com.flex.tender.service;

import com.flex.tender.model.Authority;
import com.flex.tender.model.User;
import com.flex.tender.model.enumeration.EAuthority;

public interface AuthorityService {

    public Authority getRole(EAuthority roleName);

    boolean isContractor(User user);

    boolean isBidder(User user);
    
}