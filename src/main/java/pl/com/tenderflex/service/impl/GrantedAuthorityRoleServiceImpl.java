package pl.com.tenderflex.service.impl;

import static java.util.stream.Collectors.toSet;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.GrantedAuthorityRoleRepository;
import pl.com.tenderflex.model.GrantedAuthorityRole;
import pl.com.tenderflex.model.User;
import pl.com.tenderflex.model.enums.ERole;
import pl.com.tenderflex.service.GrantedAuthorityRoleService;

@Service
@RequiredArgsConstructor
public class GrantedAuthorityRoleServiceImpl implements GrantedAuthorityRoleService {

    private final Map<ERole, GrantedAuthorityRole> roleCache = new EnumMap<>(ERole.class); 
    
    private final GrantedAuthorityRoleRepository roleRepository;
    
    @Override
    public GrantedAuthorityRole getRole(ERole roleName) {
        return roleCache.computeIfAbsent(roleName, userRole -> roleRepository.getByName(roleName));
    }
    
    @Override
    public List<GrantedAuthorityRole> getAllByUser(Integer userId) {
        return roleRepository.getByUser(userId);
    }
    
    @Override
    public boolean isContractor(User user) {
        return extractRoles(user).contains(ERole.CONTRACTOR.name());
    }

    @Override
    public boolean isBidder(User user) {
        return extractRoles(user).contains(ERole.BIDDER.name());
    }
    
    private Set<String> extractRoles(UserDetails userDetails) {
        return userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(toSet());
    }
    
}