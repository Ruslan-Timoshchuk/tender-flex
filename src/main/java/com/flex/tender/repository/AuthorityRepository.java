package com.flex.tender.repository;

import java.util.List;

import com.flex.tender.model.Authority;
import com.flex.tender.model.enumeration.ERole;

public interface AuthorityRepository {

    List<Authority> findByUser(Integer userId);

    Authority findByName(ERole name);
    
}