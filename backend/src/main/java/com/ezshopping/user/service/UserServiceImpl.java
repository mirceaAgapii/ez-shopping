package com.ezshopping.user.service;

import com.ezshopping.common.Mapper;
import com.ezshopping.user.UserRole;
import com.ezshopping.user.exceptions.WrongPasswordProvidedException;
import com.ezshopping.user.model.PasswordChangeDTO;
import com.ezshopping.user.model.UserDTO;
import com.ezshopping.user.model.User;
import com.ezshopping.user.repository.UserRepository;
import com.ezshopping.user.exceptions.UserAlreadyInDatabaseException;
import com.ezshopping.user.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import static java.util.Objects.*;

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Mapper<User, UserDTO> mapper;

    //TODO: move to a separate service
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username [" + username + "] not found in database"));
        log.info("User found in database: {}", username);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAll() {
        return userRepository.findAll().stream().map(mapper::map).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
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
    public void registerUser(UserDTO userDTO) throws UserAlreadyInDatabaseException {
        if(userExists(userDTO)) {
            log.warn("User with username [{}] or email [{}] is already in database", userDTO.getUsername(), userDTO.getEmail());
            throw new UserAlreadyInDatabaseException("User is already in db");
        }

        log.info("User with id [{}] is not known in database. Trying to save him", userDTO.getUsername());
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setRole(UserRole.CLIENT.getValue());
        userRepository.save(user);
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
        //TODO: make difference between cases with username already exists and email already exists
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            return true;
        } else if (userRepository.existsByEmail(userDTO.getEmail())) {
            return true;
        }
        return false;
    }

    private User getUserById(String id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }
}
