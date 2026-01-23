package com.flex.tender.service;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import com.flex.tender.model.Authority;
import com.flex.tender.model.enumeration.EAuthority;

public interface AuthorityService {

    Authority getRole(EAuthority roleName);

    boolean hasAuthority(Collection<? extends GrantedAuthority> authorities, EAuthority title);

}