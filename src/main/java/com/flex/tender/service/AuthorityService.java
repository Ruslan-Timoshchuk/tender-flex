package com.flex.tender.service;

import java.util.Collection;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.flex.tender.model.Authority;
import com.flex.tender.model.enumeration.EAuthority;

public interface AuthorityService {

    Authority getRole(EAuthority roleName);

    boolean hasAuthority(Collection<? extends GrantedAuthority> authorities, EAuthority title);

    Set<SimpleGrantedAuthority> toGrantedAuthorities(Set<String> authorityNames);

}