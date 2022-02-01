package com.ezshopping.user.service;

import com.ezshopping.user.model.dto.PasswordChangeDTO;
import com.ezshopping.user.model.dto.UserDTO;
import com.ezshopping.user.model.entity.User;
import com.ezshopping.user.exceptions.UserAlreadyInDatabaseException;
import com.ezshopping.user.exceptions.UserNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public interface UserService {
    UserDTO getUserDTOByUsername(String username) throws UserNotFoundException;

    User getUserByUsername(String username) throws UserNotFoundException;

    UserDTO getUserByEmail(String email) throws UserNotFoundException;

    List<User> getAll();
    List<UserDTO> getAllAsDTO();

    UserDTO getUserDTOById(String id) throws UserNotFoundException;

    void registerUser(UserDTO userDTO) throws UserAlreadyInDatabaseException, MessagingException, IOException;
    UserDTO deleteUserById(String id) throws UserNotFoundException;
    void updateUser(UserDTO updatedUser) throws UsernameNotFoundException;

    void changePassword(String id, PasswordChangeDTO passwordChangeDTO);

    User getUserById(String userId);
}
