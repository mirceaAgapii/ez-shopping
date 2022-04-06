package com.ezshopping.user.service;

import com.ezshopping.common.Mapper;
import com.ezshopping.mail.service.EmailSender;
import com.ezshopping.user.UserRole;
import com.ezshopping.user.exceptions.WrongPasswordProvidedException;
import com.ezshopping.user.model.dto.PasswordChangeDTO;
import com.ezshopping.user.model.dto.UserDTO;
import com.ezshopping.user.model.entity.User;
import com.ezshopping.user.repository.UserRepository;
import com.ezshopping.user.exceptions.UserAlreadyInDatabaseException;
import com.ezshopping.user.exceptions.UserNotFoundException;
import com.ezshopping.util.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements  UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Mapper<User, UserDTO> mapper;
    private final EmailSender emailSender;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           Mapper<User, UserDTO> mapper,
                           EmailSender emailSender) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
        this.emailSender = emailSender;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public List<UserDTO> getAllAsDTO() {
        return getAll().stream().map(mapper::map).collect(Collectors.toList());
    }

    @Override
    public User getUserByUsername(String username) throws UserNotFoundException{
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username [" + username + "] not found"));
    }

    @Override
    public UserDTO getUserDTOByUsername(String username) throws UserNotFoundException {
        User user = getUserByUsername(username);
        return mapper.map(user);
    }

    @Override
    public UserDTO getUserByEmail(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email [" + email + "] not found"));
        return mapper.map(user);
    }

    @Override
    public UserDTO getUserDTOById(String id) throws UserNotFoundException {
        User user = getUserById(id);
        return mapper.map(user);
    }

    @Override
    @Transactional
    public void registerUser(UserDTO userDTO) throws UserAlreadyInDatabaseException, MessagingException, IOException {
        if(userExists(userDTO)) {
            log.warn("User with username [{}] or email [{}] is already in database", userDTO.getUsername(), userDTO.getEmail());
            throw new UserAlreadyInDatabaseException("User is already in db");
        }

        log.info("User with id [{}] is not known in database. Trying to save him", userDTO.getUsername());
        User user = new User();
        user.setId(Utilities.getNewUuid());
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setRole(UserRole.CLIENT.getValue());
        userRepository.save(user);

        //emailSender.sendEmail(user);
    }

    @Override
    @Transactional
    public UserDTO deleteUserById(String id) throws UserNotFoundException {
        User user = getUserById(id);
        userRepository.delete(user);
        return mapper.map(user);
    }

    @Override
    @Transactional
    public void updateUser(UserDTO updatedUser) throws UserNotFoundException {
        User userEntity = getUserById(updatedUser.getId());

        userEntity.setEmail(updatedUser.getEmail());
        userEntity.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        userEntity.setUsername(updatedUser.getUsername());
        userEntity.setRole(updatedUser.getRole());

        userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void changePassword(String id, PasswordChangeDTO passwordChangeDTO) {
        User user = getUserById(id);
        if (passwordEncoder.matches(passwordChangeDTO.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(passwordChangeDTO.getNewPassword()));
        } else {
            throw new WrongPasswordProvidedException("Wrong password");
        }
    }

    private boolean userExists(UserDTO userDTO) throws UserNotFoundException{
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            return true;
        } else return userRepository.existsByEmail(userDTO.getEmail());
    }

    public User getUserById(String id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }
}
