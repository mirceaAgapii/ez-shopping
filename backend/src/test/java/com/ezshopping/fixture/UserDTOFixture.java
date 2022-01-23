package com.ezshopping.fixture;

import com.ezshopping.user.model.dto.UserDTO;

public class UserDTOFixture {

    private static final UserDTO userDTO;

    static {
        userDTO = UserDTO.builder()
                .username("TestUser1")
                .email("test1@mail.com")
                .role("CLIENT")
                .id("testId1")
                .build();
    }

    public static UserDTO userDTO() {
        return userDTO;
    }
}
