package com.ezshopping.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Utilities {

    //TODO: change the name of the header for something more
    private static final String REQUEST_HEADER_PREFIX = "Bearer ";

    public static boolean checkAuthorisationHeader(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith(REQUEST_HEADER_PREFIX);
    }

    public static DecodedJWT decodedJWTFromAuthorizationHeader(String authorizationHeader) {
        String token = getRefreshToken(authorizationHeader);
        Algorithm algorithm = getSecretAlgorithm();
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    public static String getRefreshToken(String authorizationHeader) {
        return authorizationHeader.substring(REQUEST_HEADER_PREFIX.length());
    }

    public static Algorithm getSecretAlgorithm() {
        return Algorithm.HMAC256(System.getenv("SECRET_ALGORITHM").getBytes());
    }

    public static Map<String, String> getErrorMessageMap(HttpServletResponse response, Exception exception) {
        log.error("Exception during authorization: {}", exception.getMessage());
        response.setHeader("error", exception.getMessage());
        response.setStatus(HttpStatus.FORBIDDEN.value());

        Map<String, String> error = new HashMap<>();
        error.put("error_message", exception.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return error;
    }

    public static Map<String, String> getTokenMap(HttpServletResponse response,
                                                  String access_token,
                                                  String refresh_token) {
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return tokens;
    }
}
