package com.flex.tender.repository;

import java.util.List;
import com.flex.tender.model.Authority;
import com.flex.tender.model.enumeration.EAuthority;

public interface AuthorityRepository {

    List<Authority> findAll();

    Authority findByName(EAuthority name);
    
}