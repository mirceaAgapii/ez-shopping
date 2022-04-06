package com.ezshopping.user.controller;

import static com.ezshopping.api.EndpointsAPI.*;

import com.ezshopping.config.security.service.SecurityService;
import com.ezshopping.user.model.dto.PasswordChangeDTO;
import com.ezshopping.user.model.dto.UserDTO;
import com.ezshopping.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

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
        return ResponseEntity.ok().body(userService.getAllAsDTO());
    }

    @GetMapping("/user")
    public ResponseEntity<UserDTO> getUserById(@RequestParam(name = "id") String id) {
        log.info("UserControllerREST.getUserById: received a GET request");
        return ResponseEntity.ok().body(userService.getUserDTOById(id));
    }

    @DeleteMapping("/user")
    public ResponseEntity<UserDTO> deleteUserById(@RequestParam(name = "id", required = false) String id) {
        log.info("UserControllerREST.deleteUserById: received a DELETE request");
        return ResponseEntity.ok().body(userService.deleteUserById(id));

    }

    @PostMapping("/save")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid UserDTO userDTO) throws MessagingException, IOException {
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

    @PatchMapping("/user/password")
    public ResponseEntity<Void> changePassword(@RequestParam(name="id") String id,
                                               @RequestBody PasswordChangeDTO passwordChangeDTO) {
        log.info("UserControllerREST.changePassword: received a PATCH request for user [{}]", id);
        userService.changePassword(id, passwordChangeDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        log.info("UserControllerREST.refreshToken: received a GET request");
        securityService.getNewAccessToken(request, response);
    }

}
