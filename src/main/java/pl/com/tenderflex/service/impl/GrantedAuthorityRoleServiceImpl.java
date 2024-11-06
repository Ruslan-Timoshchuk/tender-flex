package pl.com.tenderflex.service.impl;

import java.util.EnumMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.GrantedAuthorityRoleRepository;
import pl.com.tenderflex.model.GrantedAuthorityRole;
import pl.com.tenderflex.model.enums.ERole;
import pl.com.tenderflex.service.GrantedAuthorityRoleService;

@Service
@RequiredArgsConstructor
public class GrantedAuthorityRoleServiceImpl implements GrantedAuthorityRoleService {

    private final Map<ERole, GrantedAuthorityRole> roleCache = new EnumMap<>(ERole.class); 
    
    private final GrantedAuthorityRoleRepository roleRepository;
    
    public GrantedAuthorityRole getRole(ERole roleName) {
        return roleCache.computeIfAbsent(roleName, userRole -> roleRepository.getByName(roleName));
    }
    
}