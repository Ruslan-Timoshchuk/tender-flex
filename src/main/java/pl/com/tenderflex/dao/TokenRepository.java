package pl.com.tenderflex.dao;

import pl.com.tenderflex.model.Token;

public interface TokenRepository {

    boolean isValidTokenExists(String jwtToken);

    void saveUserToken(Token token);

    void revokeAllUserTokens(Integer userId);

}