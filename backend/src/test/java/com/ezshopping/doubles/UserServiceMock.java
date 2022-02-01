package com.ezshopping.doubles;

import com.ezshopping.user.exceptions.UserAlreadyInDatabaseException;
import com.ezshopping.user.exceptions.UserNotFoundException;
import com.ezshopping.user.model.dto.PasswordChangeDTO;
import com.ezshopping.user.model.dto.UserDTO;
import com.ezshopping.user.model.entity.User;
import com.ezshopping.user.service.UserService;
import com.ezshopping.util.Utilities;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class UserServiceMock implements UserService {

    private List<User> fakeUserRepository;
    private List<UserDTO> fakeUserDTORepository;

    public UserServiceMock(List<User> fakeUserRepository,
                           List<UserDTO> fakeUserDTORepository) {
        this.fakeUserRepository = fakeUserRepository;
        this.fakeUserDTORepository = fakeUserDTORepository;
    }

    @Override
    public UserDTO getUserDTOByUsername(String username) throws UserNotFoundException {
        return fakeUserDTORepository
                .stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public User getUserByUsername(String username) throws UserNotFoundException {
        return fakeUserRepository
                .stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public UserDTO getUserByEmail(String email) throws UserNotFoundException {
        return fakeUserDTORepository
                .stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public List<User> getAll() {
        return fakeUserRepository;
    }

    @Override
    public List<UserDTO> getAllAsDTO() {
        return fakeUserDTORepository;
    }

    @Override
    public UserDTO getUserDTOById(String id) throws UserNotFoundException {
        return fakeUserDTORepository
                .stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public void registerUser(UserDTO userDTO) throws UserAlreadyInDatabaseException {
        fakeUserDTORepository.add(userDTO);
        User user = User.builder()
                        .username(userDTO.getUsername())
                        .email(userDTO.getEmail())
                        .password(userDTO.getPassword())
                        .role(userDTO.getRole())
                        .build();
        user.setId(Utilities.getNewUuid());
        fakeUserRepository.add(user);
    }

    @Override
    public UserDTO deleteUserById(String id) throws UserNotFoundException {
        try {
            User user = fakeUserRepository
                    .stream()
                    .filter(u -> u.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new UserNotFoundException(""));
            fakeUserRepository.remove(user);
            UserDTO userDTO = fakeUserDTORepository
                    .stream()
                    .filter(u -> u.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new UserNotFoundException(""));
            fakeUserDTORepository.remove(userDTO);
            return userDTO;
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("");
        }
    }

    @Override
    public void updateUser(UserDTO updatedUser) throws UsernameNotFoundException {
        User user = fakeUserRepository
                .stream()
                .filter(u -> u.getId().equals(updatedUser.getId()))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(""));
        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword());
        user.setEmail(updatedUser.getEmail());
        user.setRole(updatedUser.getRole());
    }

    @Override
    public void changePassword(String id, PasswordChangeDTO passwordChangeDTO) {
        User user = fakeUserRepository
                .stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(""));
        if(user.getPassword().equals(passwordChangeDTO.getOldPassword())) {
            user.setPassword(passwordChangeDTO.getNewPassword());
        }
    }

    @Override
    public User getUserById(String userId) {
        return fakeUserRepository
                .stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(""));
    }
}
