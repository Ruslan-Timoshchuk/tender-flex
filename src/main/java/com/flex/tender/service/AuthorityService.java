package com.flex.tender.service;

import com.flex.tender.model.Authority;
import com.flex.tender.model.enumeration.EAuthority;

public interface AuthorityService {

    Authority findByName(EAuthority name);

}