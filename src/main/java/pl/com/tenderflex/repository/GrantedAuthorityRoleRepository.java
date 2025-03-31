package pl.com.tenderflex.repository;

import java.util.List;
import pl.com.tenderflex.model.GrantedAuthorityRole;
import pl.com.tenderflex.model.enums.ERole;

public interface GrantedAuthorityRoleRepository {

    List<GrantedAuthorityRole> findByUser(Integer userId);

    GrantedAuthorityRole findByName(ERole name);
    
}