package com.ezshopping.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import static com.ezshopping.api.EndpointsAPI.*;
import com.ezshopping.model.AbstractController;
import com.ezshopping.user.exceptions.UserAlreadyInDatabaseException;
import com.ezshopping.user.exceptions.UserNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;

import static com.ezshopping.util.Utilities.*;

@RestController
@RequestMapping(API + USERS)
@Slf4j
public class UserControllerREST extends AbstractController<UserEntity>{

    @Autowired
    private UserService userService;

    @Override
    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllEntities() {
        log.info("UserController.getAllEntities: GET request received");
        return ResponseEntity.ok().body(userService.getAll());
    }

    @GetMapping("/user")
    public ResponseEntity getUserById(@RequestParam(name = "id") String id) {
        try {
            return ResponseEntity.ok().body(userService.getUserById(id));
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        }
    }

    @DeleteMapping("/user")
    public ResponseEntity deleteUserById(@RequestParam(name = "id") String id) {
        try{
            return ResponseEntity.ok().body(userService.deleteUserById(id));
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        }
    }

    @PostMapping("/save")
    public ResponseEntity registerUser(@RequestBody UserDTO user) {
        UserEntity newUser = UserEntity.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .role(UserRole.CLIENT.getValue()).build();
        newUser.setId(UUID.randomUUID().toString());
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/save").toUriString());
        ResponseEntity responseEntity;
        try {
            responseEntity = ResponseEntity.created(uri).body(userService.registerUser(newUser));
            log.info("New user [{}] successfully saved", newUser);
        } catch (UserAlreadyInDatabaseException ex) {
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        return responseEntity;
    }

    @PatchMapping("/user")
    public ResponseEntity updateUser(@RequestBody UserDTO userDTO) {
        UserEntity user = UserEntity.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .build();
        user.setId(userDTO.getId());
        ResponseEntity<UserEntity> responseEntity;
        try {
            responseEntity = ResponseEntity.ok().body(userService.updateUser(user));
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        }
        return responseEntity;
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (checkAuthorisationHeader(authorizationHeader)) {
            try {
                DecodedJWT decodedJWT = decodedJWTFromAuthorizationHeader(authorizationHeader);
                String username = decodedJWT.getSubject();
                UserEntity user = userService.getUserByUsername(username);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date((System.currentTimeMillis() + 10 * 60 * 1000)))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", new ArrayList<>(Collections.singleton(user.getRole())))
                        .sign(getSecretAlgorithm());

                new ObjectMapper().writeValue(response.getOutputStream(),
                        getTokenMap(response,
                                access_token,
                                getRefreshToken(authorizationHeader)));

            } catch (Exception exception) {
                new ObjectMapper().writeValue(response.getOutputStream(), getErrorMessageMap(response, exception));
            }
        } else {
            throw new RuntimeException();
        }
    }
}
