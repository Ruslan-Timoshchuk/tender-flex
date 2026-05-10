package com.flex.tender.service.impl;

import java.util.EnumMap;
import java.util.Map;
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
    public Authority findByName(EAuthority roleName) {
        return roleCache.computeIfAbsent(roleName, userRole -> authorityRepository.findByName(roleName));
    }

}