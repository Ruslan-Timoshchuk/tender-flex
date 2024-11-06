package pl.com.tenderflex.service;

import pl.com.tenderflex.model.GrantedAuthorityRole;
import pl.com.tenderflex.model.enums.ERole;

public interface GrantedAuthorityRoleService {

    public GrantedAuthorityRole getRole(ERole roleName);
    
}