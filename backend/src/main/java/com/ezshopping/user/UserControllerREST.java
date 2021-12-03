package com.ezshopping.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import static com.ezshopping.api.EndpointsAPI.*;
import com.ezshopping.model.AbstractController;
import com.ezshopping.user.exceptions.UserAlreadyInDatabaseException;
import com.ezshopping.user.exceptions.UserNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static com.ezshopping.util.Utilities.*;

@RestController
@RequestMapping(API + USERS)
@RequiredArgsConstructor
@Slf4j
public class UserControllerREST extends AbstractController<UserEntity>{

    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Override
    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllEntities() {
        log.info("UserController.getAllEntities: GET request received");
        return ResponseEntity.ok().body(Collections.emptyList());
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserById(@RequestParam(name = "id") String id) {
        try {
            return ResponseEntity.ok().body(userService.getUserById(id));
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        }
    }

    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUserById(@RequestParam(name = "id") String id) {
        try{
            return ResponseEntity.ok().body(userService.deleteUserById(id));
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO user) {
        UserEntity newUser = UserEntity.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .role(UserRole.CLIENT.getValue()).build();
        newUser.setId(UUID.randomUUID().toString());
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/save").toUriString());
        ResponseEntity<UserEntity> responseEntity;
        try {
            responseEntity = ResponseEntity.created(uri).body(userService.registerUser(newUser));
            log.info("New user [{}] successfully saved", newUser);
        } catch (UserAlreadyInDatabaseException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        return responseEntity;
    }

    @PatchMapping("/user")
    public ResponseEntity<Void> updateUser(@RequestBody @Valid UserDTO userDTO) {
        userService.updateUser(userDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<String>> handleConstraintValidationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        List<String> constraintViolationMessages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(constraintViolationMessages);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userNotFoundException.getMessage());
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO: move to a service
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (checkAuthorisationHeader(authorizationHeader)) {
            try {
                DecodedJWT decodedJWT = decodedJWTFromAuthorizationHeader(authorizationHeader);
                String username = decodedJWT.getSubject();
                UserEntity user = userService.getUserByUsername(username);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        //TODO: using currentTimeMillis is not the best option
                        .withExpiresAt(new Date((System.currentTimeMillis() + 10 * 60 * 1000)))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", new ArrayList<>(Collections.singleton(user.getRole())))
                        .sign(getSecretAlgorithm());

                objectMapper.writeValue(response.getOutputStream(),
                        getTokenMap(response,
                                access_token,
                                getRefreshToken(authorizationHeader)));

            } catch (Exception exception) {
                objectMapper.writeValue(response.getOutputStream(), getErrorMessageMap(response, exception));
            }
        } else {
            throw new RuntimeException();
        }
    }
}
