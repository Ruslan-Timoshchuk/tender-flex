package pl.com.tenderflex.dao;

import java.util.List;
import pl.com.tenderflex.model.GrantedAuthorityRole;
import pl.com.tenderflex.model.enums.ERole;

public interface GrantedAuthorityRoleRepository {

    List<GrantedAuthorityRole> getByUser(Integer userId);

    GrantedAuthorityRole getByName(ERole name);
    
}