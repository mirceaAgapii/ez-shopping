package com.ezshopping.doubles;

import com.ezshopping.fixture.UserFixture;
import com.ezshopping.user.exceptions.UserAlreadyInDatabaseException;
import com.ezshopping.user.exceptions.UserNotFoundException;
import com.ezshopping.user.model.dto.PasswordChangeDTO;
import com.ezshopping.user.model.dto.UserDTO;
import com.ezshopping.user.model.entity.User;
import com.ezshopping.user.service.UserService;
import org.assertj.core.util.Lists;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class UserServiceSpy implements UserService {

    private int invocationsOf_getUserDTOByUsername;
    private int invocationsOf_getUserByUsername;
    private int invocationsOf_getUserByEmail;
    private int invocationsOf_getAll;
    private int invocationsOf_getAllAsDTO;
    private int invocationsOf_getUserDTOById;
    private int invocationsOf_registerUser;
    private int invocationsOf_deleteUserById;
    private int invocationsOf_updateUser;
    private int invocationsOf_changePassword;
    private int invocationsOf_getUserById;

    public int getInvocationsOf_registerUser() {
        return invocationsOf_registerUser;
    }

    public int getInvocationsOf_deleteUserById() {
        return invocationsOf_deleteUserById;
    }

    public int getInvocationsOf_updateUser() {
        return invocationsOf_updateUser;
    }

    public int getInvocationsOf_changePassword() {
        return invocationsOf_changePassword;
    }

    public int getInvocationsOf_getUserDTOByUsername() {
        return invocationsOf_getUserDTOByUsername;
    }

    public int getInvocationsOf_getUserByUsername() {
        return invocationsOf_getUserByUsername;
    }

    public int getInvocationsOf_getUserByEmail() {
        return invocationsOf_getUserByEmail;
    }

    public int getInvocationsOf_getAll() {
        return invocationsOf_getAll;
    }

    public int getInvocationsOf_getAllAsDTO() {
        return invocationsOf_getAllAsDTO;
    }

    public int getInvocationsOf_getUserDTOById() {
        return invocationsOf_getUserDTOById;
    }

    public int getInvocationsOf_getUserById() {
        return invocationsOf_getUserById;
    }

    @Override
    public UserDTO getUserDTOByUsername(String username) throws UserNotFoundException {
        invocationsOf_getUserDTOByUsername++;
        return UserDTO.builder().build();
    }

    @Override
    public User getUserByUsername(String username) throws UserNotFoundException {
        invocationsOf_getUserByUsername++;
        return User.builder().build();
    }

    @Override
    public UserDTO getUserByEmail(String email) throws UserNotFoundException {
        invocationsOf_getUserByEmail++;
        return UserDTO.builder().build();
    }

    @Override
    public List<User> getAll() {
        invocationsOf_getAll++;
        return Lists.emptyList();
    }

    @Override
    public List<UserDTO> getAllAsDTO() {
        invocationsOf_getAllAsDTO++;
        return Lists.emptyList();
    }

    @Override
    public UserDTO getUserDTOById(String id) throws UserNotFoundException {
        invocationsOf_getUserDTOById++;
        return UserDTO.builder().build();
    }

    @Override
    public void registerUser(UserDTO userDTO) throws UserAlreadyInDatabaseException {
        invocationsOf_registerUser++;
    }

    @Override
    public UserDTO deleteUserById(String id) throws UserNotFoundException {
        invocationsOf_deleteUserById++;
        return UserDTO.builder().build();
    }

    @Override
    public void updateUser(UserDTO updatedUser) throws UsernameNotFoundException {
        invocationsOf_updateUser++;
    }

    @Override
    public void changePassword(String id, PasswordChangeDTO passwordChangeDTO) {
        invocationsOf_changePassword++;
    }

    @Override
    public User getUserById(String userId) {
        invocationsOf_getUserById++;
        return UserFixture.user();
    }


}
