package pl.com.tenderflex.dao;

import java.util.Set;

import pl.com.tenderflex.model.GrantedAuthorityRole;
import pl.com.tenderflex.model.enums.ERole;

public interface GrantedAuthorityRoleRepository {

    Set<GrantedAuthorityRole> getByUser(Integer userId);

    GrantedAuthorityRole getByName(ERole name);
    
}