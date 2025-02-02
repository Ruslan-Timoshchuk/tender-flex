package pl.com.tenderflex.service;

import java.util.List;
import pl.com.tenderflex.model.GrantedAuthorityRole;
import pl.com.tenderflex.model.User;
import pl.com.tenderflex.model.enums.ERole;

public interface GrantedAuthorityRoleService {

    public GrantedAuthorityRole getRole(ERole roleName);
    
    List<GrantedAuthorityRole> getAllByUser(Integer userId);

    boolean isContractor(User user);

    boolean isBidder(User user);
    
}