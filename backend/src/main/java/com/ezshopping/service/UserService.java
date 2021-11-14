package com.ezshopping.service;

import com.ezshopping.dao.UserRepository;
import com.ezshopping.model.UserEntity;
import com.ezshopping.model.UserRole;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean addUser(String userName) {
        UserEntity newUser = new UserEntity(userName, UserRole.CLIENT.getValue());
        userRepository.save(newUser);
        return true;
    }

}
