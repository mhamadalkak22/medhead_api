package com.medhead.medhead.utils;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.medhead.medhead.Exceptions.DataException;
import com.medhead.medhead.ResponseObjects.BaseResponse;
import com.medhead.medhead.entities.AccountType;
import com.medhead.medhead.entities.AppUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class Utils {

    private static int authTokenLife = 5; // en minutes

    private static int refreshTokenLife = 5; // en jours

    public static long CONNECTED_USER_ID;

    // NE PAS UTILISER EN PRODUCTION
    public static String getEncodingSecret() {
        return "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";
    }

    public static String generateAuthenticationJWT(AppUser user) {

        var username = user.getUsername();
        var userId = user.getId();
        var accountType = user.getAccountType().getName();

        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(Utils.getEncodingSecret()),
                SignatureAlgorithm.HS256.getJcaName());
        String jwtToken = Jwts.builder()
                .claim("username", username)
                .claim("userID", userId)
                .claim("accountType", accountType)
                .setSubject(username)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(authTokenLife, ChronoUnit.MINUTES)))
                .signWith(hmacKey)
                .compact();

        return jwtToken;
    }

    public static String generateRefreshToken(AppUser user) {
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(Utils.getEncodingSecret()),
                SignatureAlgorithm.HS256.getJcaName());

        var username = user.getUsername();
        var userId = user.getId();
        var accountType = user.getAccountType().getName();

        String jwtToken = Jwts.builder()
                .claim("username", username)
                .claim("userID", userId)
                .claim("accountType", accountType)
                .setSubject("refresh")
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(refreshTokenLife, ChronoUnit.DAYS)))
                .signWith(hmacKey)
                .compact();

        return jwtToken;
    }

    public static BaseResponse validateJWT(Map<String, String> headers) throws DataException {

        BaseResponse response = new BaseResponse(); // permet de stocker le nouveau token en cas d'expiration
        String jwtString = headers.get("token");

        if (jwtString == null) {
            throw new DataException("Jetton d'authentification incorrect");
        }

        String secret = Utils.getEncodingSecret();
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());

        try {
            Jwts.parserBuilder()
                    .setSigningKey(hmacKey)
                    .build()
                    .parseClaimsJws(jwtString);

        } catch (ExpiredJwtException e) {
            response.setNewAuthenticationToken(generateNewToken());

        }

        return response;
        // la methode parseClaimsJws Verifie automatiquement l'expiration du token

    }

    public static Cookie[] getCookies() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();

        HttpServletRequest request = requestAttributes.getRequest();

        return request.getCookies();

    }

    public static String getRefreshToken() {
        Map<String, Cookie> cookiesMap = new HashMap<String, Cookie>();
        for (Cookie cookie : getCookies()) {
            cookiesMap.put(cookie.getName(), cookie);
        }

        return cookiesMap.get("refresh-token").getValue();

    }

    public static String generateNewToken() throws DataException {

        String refreshToken = getRefreshToken();
        if (refreshToken == null) {
            throw new DataException("Session expirée");
        }
        String secret = Utils.getEncodingSecret();
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());

        Jws<Claims> jwt = null;

        try {
            jwt = Jwts.parserBuilder()
                    .setSigningKey(hmacKey)
                    .build()
                    .parseClaimsJws(refreshToken);

            var jwtBody = jwt.getBody();
            var userIdInt = (Integer) jwtBody.get("userID");
            Long userId = Long.valueOf(userIdInt);
            var username = (String) jwtBody.get("username");
            var accountType = (String) jwtBody.get("accountType");

            AccountType typeToSetToUserForToken = new AccountType();
            typeToSetToUserForToken.setName(accountType);
            AppUser user = new AppUser();
            user.setUsername(username);
            user.setId(userId);
            user.setAccountType(typeToSetToUserForToken);

            String newAuthenticationToken = generateAuthenticationJWT(user);
            return newAuthenticationToken;

        } catch (ExpiredJwtException e) {

            throw new DataException("Session expirée");
        }

    }

    public static void setUserID(Map<String, String> headers) throws Exception {

        String jwtString = headers.get("token");

        String secret = Utils.getEncodingSecret();
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());

        Jws<Claims> jwt = Jwts.parserBuilder()
                .setSigningKey(hmacKey)
                .build()
                .parseClaimsJws(jwtString);

        var jwtBody = jwt.getBody();

        var userIdInt = (Integer) jwtBody.get("userID");
        long userId = (long) userIdInt;

        Utils.CONNECTED_USER_ID = userId;

    }

}
