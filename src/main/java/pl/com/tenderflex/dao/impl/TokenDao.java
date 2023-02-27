package pl.com.tenderflex.dao.impl;

import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.TokenRepository;
import pl.com.tenderflex.model.Token;

@Repository
@RequiredArgsConstructor
public class TokenDao implements TokenRepository {

    public static final String ADD_NEW_TOKEN_QUERY = "INSERT INTO tokens(user_id, jwt_token, token_type, revoked, expired) "
            + "VALUES (?, ?, ?, ?, ?)";
    public static final String REVOKE_ALL_USER_TOKENS_QUERY = "UPDATE tokens SET revoked = true, expired = true "
            + "WHERE user_id = ? AND revoked = false AND expired = false";
    public static final String SELECT_VALID_TOKEN_QUERY = "SELECT EXISTS(SELECT FROM tokens WHERE jwt_token = ? AND revoked = false AND expired = false)";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public boolean isValidTokenExists(String jwtToken) {
        return jdbcTemplate.queryForObject(SELECT_VALID_TOKEN_QUERY, Boolean.class, jwtToken);
    }

    @Override
    public void saveUserToken(Token token) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_TOKEN_QUERY, new String[] { "id" });
            statement.setInt(1, token.getUser().getId());
            statement.setString(2, token.getJwtToken());
            statement.setString(3, String.valueOf(token.getTokenType()));
            statement.setObject(4, token.isExpired());
            statement.setObject(5, token.isRevoked());
            return statement;
        }, keyHolder);
    }

    @Override
    public void revokeAllUserTokens(Integer userId) {
        jdbcTemplate.update(REVOKE_ALL_USER_TOKENS_QUERY, userId);
    }
}