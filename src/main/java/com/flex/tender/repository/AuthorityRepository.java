package com.flex.tender.repository;

import java.util.List;
import com.flex.tender.model.Authority;

public interface AuthorityRepository {

    List<Authority> findAll();
    
}