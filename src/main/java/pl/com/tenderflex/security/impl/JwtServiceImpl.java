package pl.com.tenderflex.security.impl;

import java.security.Key;
import java.util.Date;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import pl.com.tenderflex.exception.CookiesNotPresentException;
import pl.com.tenderflex.security.JwtService;

@Service
public class JwtServiceImpl implements JwtService {

    public static final String SLASH_LINE = "/";
    public static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
   
    @Value("${tenderflex.app.jwtCookieName}")
    private String jwtCookieName;
    @Value("${jwt.cookie.max.age}")
    private Integer jwtCookieMaxAgeSec;
    @Value("${jwt.expiration.token}")
    private Integer jwtExpirationTokenMs;

    @Override
    public ResponseCookie generateJwtCookie(String userEmail) {
        String jwt = generateTokenFromUserEmail(userEmail);
        return ResponseCookie.from(jwtCookieName, jwt).path(SLASH_LINE).maxAge(jwtCookieMaxAgeSec).httpOnly(true).build();
    }

    @Override
    public String generateTokenFromUserEmail(String userEmail) {
        return Jwts.builder()
                .setSubject(userEmail)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationTokenMs))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookieName);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            throw new CookiesNotPresentException("There are invalid cookies in request");
        }
    }

    @Override
    public boolean isJwtTokenValid(String authToken) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(authToken) != null;
    }

    @Override
    public String getUserEmailFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody().getSubject();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}