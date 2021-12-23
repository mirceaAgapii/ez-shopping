package com.ezshopping.user.service;

import com.ezshopping.user.model.UserDTO;
import com.ezshopping.user.model.User;
import com.ezshopping.user.exceptions.UserAlreadyInDatabaseException;
import com.ezshopping.user.exceptions.UserNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {
    UserDTO getUserDTOByUsername(String username) throws UserNotFoundException;

    User getUserByUsername(String username) throws UserNotFoundException;

    UserDTO getUserByEmail(String email) throws UserNotFoundException;

    List<UserDTO> getAll();

    UserDTO getUserDTOById(String id) throws UserNotFoundException;

    void registerUser(UserDTO userDTO) throws UserAlreadyInDatabaseException;
    UserDTO deleteUserById(String id) throws UserNotFoundException;
    void updateUser(UserDTO updatedUser) throws UsernameNotFoundException;
}
