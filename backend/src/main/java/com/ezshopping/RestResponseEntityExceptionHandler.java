package com.ezshopping;


import com.auth0.jwt.exceptions.JWTDecodeException;
import com.ezshopping.security.filter.HttpStatusEZ;
import com.ezshopping.user.exceptions.UserAlreadyInDatabaseException;
import com.ezshopping.user.exceptions.UserNotFoundException;
import com.ezshopping.user.exceptions.WrongPasswordProvidedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {
    //TODO: send a DTO instead: with timestamp, endpoint/operation name, exception message
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
    public ResponseEntity<String> handleJWTDecodeException(JWTDecodeException jwtDecodeException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jwtDecodeException.getMessage());
    }

    @ExceptionHandler(UserAlreadyInDatabaseException.class)
    public ResponseEntity<String> handleUserAlreadyInDatabaseException(UserAlreadyInDatabaseException userAlreadyInDatabaseException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userAlreadyInDatabaseException.getMessage());
    }

    @ExceptionHandler(WrongPasswordProvidedException.class)
    public  ResponseEntity<String> handleWrongPasswordProvidedException(WrongPasswordProvidedException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

}
