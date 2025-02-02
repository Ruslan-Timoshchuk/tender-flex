package pl.com.tenderflex.service;

import java.util.function.Function;
import pl.com.tenderflex.model.User;

public interface RoleBasedActionExecutor {

    <T> T executeRoleBasedAction(User user, Function<User, T> contractorAction,
            Function<User, T> bidderAction);
    
}