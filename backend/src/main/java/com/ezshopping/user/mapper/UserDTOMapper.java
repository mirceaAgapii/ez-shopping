package com.ezshopping.user.mapper;

import com.ezshopping.model.Mapper;
import com.ezshopping.user.UserDTO;
import com.ezshopping.user.UserEntity;
import org.springframework.stereotype.Component;

@Component
class UserDTOMapper implements Mapper<UserEntity, UserDTO> {
    @Override
    public UserDTO map(UserEntity entity) {
        return UserDTO.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .build();
    }
}
