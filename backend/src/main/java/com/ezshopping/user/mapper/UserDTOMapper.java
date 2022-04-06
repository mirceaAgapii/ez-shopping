package com.ezshopping.user.mapper;

import com.ezshopping.common.Mapper;
import com.ezshopping.user.model.dto.UserDTO;
import com.ezshopping.user.model.entity.User;
import org.springframework.stereotype.Component;

@Component
class UserDTOMapper implements Mapper<User, UserDTO> {
    @Override
    public UserDTO map(User entity) {
        return UserDTO.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .id(entity.getId())
                .role(entity.getRole())
                .build();
    }
}
