package com.ezshopping.user.controller;

import static com.ezshopping.api.EndpointsAPI.*;
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
        return ResponseEntity.ok().body(userService.getAll());
    }

    @GetMapping("/user")
    public ResponseEntity<UserDTO> getUserById(@RequestParam(name = "id") String id) {
        return ResponseEntity.ok().body(userService.getUserDTOById(id));
    }

    @DeleteMapping("/user")
    public ResponseEntity<UserDTO> deleteUserById(@RequestParam(name = "id") String id) {
        return ResponseEntity.ok().body(userService.deleteUserById(id));

    }

    @PostMapping("/save")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid UserDTO userDTO) {
        userService.registerUser(userDTO);
        log.info("New user [{}] successfully saved", userDTO.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/user")
    public ResponseEntity<Void> updateUser(@RequestBody @Valid UserDTO userDTO) {
        userService.updateUser(userDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        securityService.getNewAccessToken(request, response);
    }
//TODO: move to contollerAdvice
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
}
