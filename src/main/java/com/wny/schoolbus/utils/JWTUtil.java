package com.wny.schoolbus.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.wny.schoolbus.enums.ApiErrorCode;
import com.wny.schoolbus.exceptions.JwtException;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

@Component
public class JWTUtil {
    @Value("${jwt.jwt-secret}")
    private String SECRET;

    @Value("${jwt.subject}")
    private String SUBJECT;

    @Value("${jwt.issuer}")
    private String ISSUER;

    @Value("${jwt.userlogin_claim}")
    private String USERLOGIN_CLAIM;

    public String generateToken(String userLogin){
        Date expirationDate = Date.from(ZonedDateTime.now().plusDays(30).toInstant());

        return JWT.create()
                .withSubject(SUBJECT)
                .withClaim(USERLOGIN_CLAIM,userLogin)
                .withIssuedAt(new Date())
                .withIssuer(ISSUER)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(SECRET));
    }

    public String validateToken(String token) {
        if (Objects.isNull(token)) {

            throw new JwtException(
                    ApiErrorCode.AUTHENTICATION_ERROR,
                    "Authorization header is null"
            );
        }

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET))
                .withSubject(SUBJECT)
                .withIssuer(ISSUER)
                .build();

        DecodedJWT jwt = jwtVerifier.verify(token);

        return jwt.getClaim(USERLOGIN_CLAIM).asString();
    }
}
