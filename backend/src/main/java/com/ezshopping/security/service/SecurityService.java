package com.ezshopping.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ezshopping.user.model.User;
import com.ezshopping.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class SecurityService {

    private final UserService userService;
    private static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";

    public boolean checkAuthorisationHeader(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith(AUTHORIZATION_HEADER_PREFIX);
    }

    public DecodedJWT decodedJWTFromAuthorizationHeader(String authorizationHeader) {
        String token = getRefreshToken(authorizationHeader);
        Algorithm algorithm = getSecretAlgorithm();
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    public String getRefreshToken(String authorizationHeader) {
        return authorizationHeader.substring(AUTHORIZATION_HEADER_PREFIX.length());
    }

    public Algorithm getSecretAlgorithm() {
        return Algorithm.HMAC256(System.getenv("SECRET_ALGORITHM").getBytes());
    }

    public Map<String, String> getErrorMessageMap(HttpServletResponse response, Exception exception) {
        log.error("Exception during authorization: {}", exception.getMessage());
        response.setHeader("error", exception.getMessage());
        response.setStatus(HttpStatus.FORBIDDEN.value());

        Map<String, String> error = new HashMap<>();
        error.put("error_message", exception.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return error;
    }

    public void getNewAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (checkAuthorisationHeader(authorizationHeader)) {
            try {
                DecodedJWT decodedJWT = decodedJWTFromAuthorizationHeader(authorizationHeader);
                String username = decodedJWT.getSubject();
                User user = userService.getUserByUsername(username);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        //TODO: using currentTimeMillis is not the best option
                        .withExpiresAt(new Date((System.currentTimeMillis() + 10 * 60 * 1000)))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", new ArrayList<>(Collections.singleton(user.getRole())))
                        .sign(getSecretAlgorithm());

                response.setHeader("access_token", access_token);
                response.setHeader("refresh_token", getRefreshToken(authorizationHeader));

            } catch (Exception exception) {

            }
        } else {
            throw new RuntimeException();
        }
    }

}
