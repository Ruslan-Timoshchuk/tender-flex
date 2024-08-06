package pl.com.tenderflex.dao;

import java.util.Set;
import pl.com.tenderflex.model.ERole;
import pl.com.tenderflex.model.GrantedAuthorityRole;

public interface GrantedAuthorityRoleRepository {

    Set<GrantedAuthorityRole> getByUser(Integer userId);

    GrantedAuthorityRole getByName(ERole name);
    
}