package com.ezshopping.config.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import static com.ezshopping.api.EndpointsAPI.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private static final String REQUEST_HEADER_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (!request.getServletPath().equals(API + LOGIN)
                && !request.getServletPath().equals(API + USERS + TOKEN + REFRESH)
                && !request.getServletPath().equals(API + USERS + REGISTRATION)
                && !request.getServletPath().equals(API + "/arduino")
                && !request.getServletPath().equals("/web-socket")) {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (checkAuthorisationHeader(authorizationHeader)) {
                DecodedJWT decodedJWT;
                try {
                    decodedJWT = decodedJWTFromAuthorizationHeader(authorizationHeader);
                    String username = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    Arrays.stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority((role))));
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } catch (JWTVerificationException ex) {
                    final String expiredMsg = ex.getMessage();
                    logger.warn(expiredMsg);

                    final String msg = (expiredMsg != null) ? expiredMsg : "Unauthorized";
                    response.sendError(HttpStatus.UNAUTHORIZED.value(), msg);
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean checkAuthorisationHeader(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith(REQUEST_HEADER_PREFIX);
    }

    private DecodedJWT decodedJWTFromAuthorizationHeader(String authorizationHeader) throws JWTDecodeException, TokenExpiredException {
        String token = getRefreshToken(authorizationHeader);
        Algorithm algorithm = getSecretAlgorithm();
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    public String getRefreshToken(String authorizationHeader) {
        return authorizationHeader.substring(REQUEST_HEADER_PREFIX.length());
    }

    public Algorithm getSecretAlgorithm() {
        return Algorithm.HMAC256(System.getenv("SECRET_ALGORITHM").getBytes());
    }

}
