package com.flex.tender.service.impl;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import com.flex.tender.model.Authority;
import com.flex.tender.model.enumeration.EAuthority;
import com.flex.tender.repository.AuthorityRepository;
import com.flex.tender.service.AuthorityService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

    private final Map<EAuthority, Authority> roleCache = new EnumMap<>(EAuthority.class);

    private final AuthorityRepository authorityRepository;

    @Override
    public Authority getRole(EAuthority roleName) {
        return roleCache.computeIfAbsent(roleName, userRole -> authorityRepository.findByName(roleName));
    }

    @Override
    public boolean hasAuthority(Collection<? extends GrantedAuthority> authorities, EAuthority title) {
        return authorities != null && authorities.stream().map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> authority.equals(title.name()));
    }

}