package com.ezshopping.user;

import com.ezshopping.model.AbstractService;
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
import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService extends AbstractService<UserEntity> implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
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

    public UserEntity getUserByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username);
        if(user != null) {
            return user;
        } else {
            throw new UserNotFoundException("User with username [" + username + "] not found");
        }
    }

    public UserEntity getUserByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        if(user != null) {
            return user;
        } else {
            throw new UserNotFoundException("User with email [" + email + "] not found");
        }
    }


    public UserEntity getUserById(String id) throws UserNotFoundException {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        UserEntity user;
        if(optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        return user;
    }

    public UserEntity registerUser(UserEntity user) throws UserAlreadyInDatabaseException {
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

    public UserEntity deleteUserById(String id) throws UserNotFoundException {
        UserEntity user = getUserById(id);
        userRepository.delete(user);
        return user;
    }

    public UserEntity updateUser(UserEntity updatedUser) throws UsernameNotFoundException {
        UserEntity userEntity = getUserById(updatedUser.getId());
        if (Objects.nonNull(updatedUser.getEmail()) && !updatedUser.getEmail().equals(userEntity.getEmail())) {
            userEntity.setEmail(updatedUser.getEmail());
        }
        if (Objects.nonNull(updatedUser.getRole()) && !updatedUser.getRole().equals(userEntity.getRole())) {
            userEntity.setRole(updatedUser.getRole());
        }
        if (Objects.nonNull(updatedUser.getPassword()) && !passwordEncoder.encode(updatedUser.getPassword()).equals(userEntity.getPassword())) {
            userEntity.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        if (Objects.nonNull(updatedUser.getUsername()) && !updatedUser.getUsername().equals(userEntity.getUsername())) {
            userEntity.setUsername(updatedUser.getUsername());
        }
        userRepository.save(userEntity);
        return userEntity;
    }

}
