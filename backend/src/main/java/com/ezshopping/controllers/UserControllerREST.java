package com.ezshopping.controllers;

import com.ezshopping.api.EndpointsAPI;
import com.ezshopping.model.UserEntity;
import com.ezshopping.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EndpointsAPI.API)
@AllArgsConstructor
@Slf4j
public class UserControllerREST {

    private UserService userService;

    @GetMapping(EndpointsAPI.USERS)
    public List<UserEntity> getAllEntities() {
        log.info("UserController.getAllEntities: GET request received");
        return userService.getAllUsers();
    }

    @PostMapping(EndpointsAPI.USERS)
    public String addUser(@RequestParam String name) {
        if (userService.addUser(name)) {
            return "User[" + name + "] saved successfully!";
        } else {
            return "User wasn't saved";
        }
    }
}
