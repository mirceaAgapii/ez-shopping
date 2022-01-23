package com.ezshopping.doubles;

import com.ezshopping.user.exceptions.UserAlreadyInDatabaseException;
import com.ezshopping.user.exceptions.UserNotFoundException;
import com.ezshopping.user.model.dto.PasswordChangeDTO;
import com.ezshopping.user.model.dto.UserDTO;
import com.ezshopping.user.model.entity.User;
import com.ezshopping.user.service.UserService;
import org.assertj.core.util.Lists;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public class UserServiceFake implements UserService {
    @Override
    public UserDTO getUserDTOByUsername(String username) throws UserNotFoundException {
        return UserDTO.builder().build();
    }

    @Override
    public User getUserByUsername(String username) throws UserNotFoundException {
        return User.builder().build();
    }

    @Override
    public UserDTO getUserByEmail(String email) throws UserNotFoundException {
        return UserDTO.builder().build();
    }

    @Override
    public List<User> getAll() {
        return Lists.emptyList();
    }

    @Override
    public List<UserDTO> getAllAsDTO() {
        return Lists.emptyList();
    }

    @Override
    public UserDTO getUserDTOById(String id) throws UserNotFoundException {
        return UserDTO.builder().build();
    }

    @Override
    public void registerUser(UserDTO userDTO) throws UserAlreadyInDatabaseException {

    }

    @Override
    public UserDTO deleteUserById(String id) throws UserNotFoundException {
        return UserDTO.builder().build();
    }

    @Override
    public void updateUser(UserDTO updatedUser) throws UsernameNotFoundException {

    }

    @Override
    public void changePassword(String id, PasswordChangeDTO passwordChangeDTO) {

    }
}
