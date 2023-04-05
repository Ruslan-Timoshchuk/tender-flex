package pl.com.tenderflex.dao;

import java.util.List;
import pl.com.tenderflex.model.Role;

public interface RoleRepository {

    List<Role> getByUser(Integer userId);
    
}