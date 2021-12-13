package com.ezshopping.user.controller;

import static com.ezshopping.api.EndpointsAPI.*;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.ezshopping.security.service.SecurityService;
import com.ezshopping.user.model.UserDTO;
import com.ezshopping.user.exceptions.UserAlreadyInDatabaseException;
import com.ezshopping.user.exceptions.UserNotFoundException;
import com.ezshopping.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(API + USERS)
@RequiredArgsConstructor
@Slf4j
public class UserControllerREST {

    private final UserService userService;
    private final SecurityService securityService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllEntities() {
        log.info("UserControllerREST.getAllEntities: received a GET request");
        return ResponseEntity.ok().body(userService.getAll());
    }

    @GetMapping("/user")
    public ResponseEntity<UserDTO> getUserById(@RequestParam(name = "id") String id) {
        log.info("UserControllerREST.getUserById: received a GET request");
        return ResponseEntity.ok().body(userService.getUserDTOById(id));
    }

    @DeleteMapping("/user")
    public ResponseEntity<UserDTO> deleteUserById(@RequestParam(name = "id") String id) {
        log.info("UserControllerREST.deleteUserById: received a DELETE request");
        return ResponseEntity.ok().body(userService.deleteUserById(id));

    }

    @PostMapping("/save")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid UserDTO userDTO) {
        log.info("UserControllerREST.registerUser: received a POST request");
        userService.registerUser(userDTO);
        log.info("UserControllerREST.registerUser: New user [{}] successfully saved", userDTO.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/user")
    public ResponseEntity<Void> updateUser(@RequestBody @Valid UserDTO userDTO) {
        log.info("UserControllerREST.updateUser: received a PATCH request");
        userService.updateUser(userDTO);
        log.info("UserControllerREST.updateUser: Updated user [{}]", userDTO.getUsername());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PostMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        log.info("UserControllerREST.refreshToken: received a GET request");
        securityService.getNewAccessToken(request, response);
    }
//TODO: move to controllerAdvice
    @ExceptionHandler(UserAlreadyInDatabaseException.class)
    public  ResponseEntity<String> handleUserAlreadyInDatabaseException(UserAlreadyInDatabaseException ex) {
        //TODO: send a DTO instead: with timestamp, endpoint/operation name, exception message
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
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

    @ExceptionHandler(JWTDecodeException.class)
    public  ResponseEntity<String> handleJWTDecodeException(JWTDecodeException jwtDecodeException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jwtDecodeException.getMessage());
    }
}
