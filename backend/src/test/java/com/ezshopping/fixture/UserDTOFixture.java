package com.ezshopping.fixture;

import com.ezshopping.user.model.dto.UserDTO;

import java.util.Arrays;
import java.util.List;

public class UserDTOFixture {

    private static final UserDTO userDTO1;
    private static final UserDTO userDTO2;

    static {
        userDTO1 = UserDTO.builder()
                .username("TestUser1")
                .email("test1@mail.com")
                .role("CLIENT")
                .id("testId1")
                .build();
        userDTO2 = UserDTO.builder()
                .username("TestUser2")
                .email("test2@mail.com")
                .role("CLIENT")
                .id("testId2")
                .build();
    }

    public static UserDTO userDTO() {
        return userDTO1;
    }

    public static List<UserDTO> userDTOList() {
        return Arrays.asList(userDTO1, userDTO2);
    }
}
