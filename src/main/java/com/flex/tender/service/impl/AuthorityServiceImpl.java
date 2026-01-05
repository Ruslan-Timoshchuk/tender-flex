package com.flex.tender.service.impl;

import static java.util.stream.Collectors.toSet;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import com.flex.tender.model.Authority;
import com.flex.tender.model.User;
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
    public boolean isContractor(User user) {
        return extractRoles(user).contains(EAuthority.CONTRACTOR.name());
    }

    @Override
    public boolean isBidder(User user) {
        return extractRoles(user).contains(EAuthority.BIDDER.name());
    }
    
    private Set<String> extractRoles(User user) {
        return user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(toSet());
    }
    
}