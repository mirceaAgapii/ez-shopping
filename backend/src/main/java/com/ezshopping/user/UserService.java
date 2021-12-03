package com.ezshopping.user;

import com.ezshopping.user.exceptions.UserAlreadyInDatabaseException;
import com.ezshopping.user.exceptions.UserNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    UserEntity getUserByUsername(String username);

    UserEntity getUserByEmail(String email);

    UserEntity getUserById(String id) throws UserNotFoundException;

    UserEntity registerUser(UserEntity user) throws UserAlreadyInDatabaseException;
    UserEntity deleteUserById(String id) throws UserNotFoundException;
    void updateUser(UserDTO updatedUser) throws UsernameNotFoundException;
}
