package pl.com.tenderflex.service.impl;

import java.util.function.Function;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.exception.UnauthorizedAccessException;
import pl.com.tenderflex.model.User;
import pl.com.tenderflex.service.GrantedAuthorityRoleService;
import pl.com.tenderflex.service.RoleBasedActionExecutor;

@Service
@RequiredArgsConstructor
public class RoleBasedActionExecutorImpl implements RoleBasedActionExecutor {

    private final GrantedAuthorityRoleService grantedAuthorityRoleService;

    @Override
    public <T> T executeRoleBasedAction(User user, Function<User, T> contractorAction,
            Function<User, T> bidderAction) {
        if (grantedAuthorityRoleService.isContractor(user)) {
            return contractorAction.apply(user);
        } else if (grantedAuthorityRoleService.isBidder(user)) {
            return bidderAction.apply(user);
        }
        throw new UnauthorizedAccessException("User does not have the required role to access this resource");
    }

}