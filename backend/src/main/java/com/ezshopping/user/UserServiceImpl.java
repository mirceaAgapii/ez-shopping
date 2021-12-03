package com.ezshopping.user;

import com.ezshopping.model.AbstractService;
import com.ezshopping.model.Mapper;
import com.ezshopping.user.exceptions.UserAlreadyInDatabaseException;
import com.ezshopping.user.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl extends AbstractService<UserEntity> implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Mapper<UserEntity, UserDTO> mapper;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("User not found in database");
            throw new UsernameNotFoundException("User not found in database");
        } else {
            log.info("User found in database: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return new User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getUserByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username);
        if(user != null) {
            return user;
        } else {
            throw new UserNotFoundException("User with username [" + username + "] not found");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getUserByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        if(user != null) {
            return user;
        } else {
            throw new UserNotFoundException("User with email [" + email + "] not found");
        }
    }


    @Override
    public UserEntity getUserById(String id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    @Override
    @Transactional
    public UserEntity registerUser(UserEntity user) throws UserAlreadyInDatabaseException {
        //TODO: don't use exception handling for flow control
        try {
            getUserByUsername(user.getUsername());
            log.warn("User with username [{}] is already in database", user.getUsername());
            throw new UserAlreadyInDatabaseException("User is already in db");
        } catch (UserNotFoundException ex) {
            try {
                getUserByEmail(user.getEmail());
                log.warn("User with email [{}] is already in database", user.getEmail());
                throw new UserAlreadyInDatabaseException("User with this email already exists");
            } catch (UserNotFoundException ex1) {
                log.info("User with id [{}] is not known in database. Trying to save him", user.getUsername());
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                return userRepository.save(user);
            }
        }
    }

    @Override
    @Transactional
    public UserEntity deleteUserById(String id) throws UserNotFoundException {
        UserEntity user = getUserById(id);
        userRepository.delete(user);
        return user;
    }

    //TODO: search for a better way of updating user data
    @Override
    @Transactional
    public void updateUser(UserDTO updatedUser) throws UsernameNotFoundException {
        UserEntity userEntity = getUserById(updatedUser.getId());

        if (Objects.nonNull(updatedUser.getEmail())) {
            userEntity.setEmail(updatedUser.getEmail());
        }
        if (Objects.nonNull(updatedUser.getPassword()) ) {
            userEntity.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        if (Objects.nonNull(updatedUser.getUsername())) {
            userEntity.setUsername(updatedUser.getUsername());
        }
        userRepository.save(userEntity);
    }

}
