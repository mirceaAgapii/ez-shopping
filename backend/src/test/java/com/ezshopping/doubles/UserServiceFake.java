package com.ezshopping.doubles;

import com.ezshopping.fixture.UserDTOFixture;
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

public class UserServiceFake implements UserService {
    @Override
    public UserDTO getUserDTOByUsername(String username) throws UserNotFoundException {
        return UserDTOFixture.userDTO();
    }

    @Override
    public User getUserByUsername(String username) throws UserNotFoundException {
        return UserFixture.user();
    }

    @Override
    public UserDTO getUserByEmail(String email) throws UserNotFoundException {
        return UserDTOFixture.userDTO();
    }

    @Override
    public List<User> getAll() {
        return UserFixture.userList();
    }

    @Override
    public List<UserDTO> getAllAsDTO() {
        return UserDTOFixture.userDTOList();
    }

    @Override
    public UserDTO getUserDTOById(String id) throws UserNotFoundException {
        return UserDTOFixture.userDTO();
    }

    @Override
    public void registerUser(UserDTO userDTO) throws UserAlreadyInDatabaseException {

    }

    @Override
    public UserDTO deleteUserById(String id) throws UserNotFoundException {
        return UserDTOFixture.userDTO();
    }

    @Override
    public void updateUser(UserDTO updatedUser) throws UsernameNotFoundException {

    }

    @Override
    public void changePassword(String id, PasswordChangeDTO passwordChangeDTO) {

    }

    @Override
    public User getUserById(String userId) {
        return UserFixture.user();
    }
}
