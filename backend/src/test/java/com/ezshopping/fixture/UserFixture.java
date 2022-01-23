package com.ezshopping.fixture;

import com.ezshopping.user.model.entity.User;

import java.util.Arrays;
import java.util.List;

public class UserFixture {

    private static final User user1;
    private static final User user2;

    static {
        user1 = User.builder()
                .username("TestUser1")
                .email("test1@mail.com")
                .role("CLIENT")
                .password("testPassword1")
                .build();
        user1.setId("testId1");

        user2 = User.builder()
                .username("TestUser2")
                .email("test2@mail.com")
                .role("CLIENT")
                .password("testPassword2")
                .build();
        user2.setId("testId2");
    }

    public static User user() {
        return user1;
    }

    public static List<User> userList() {
        return Arrays.asList(user1, user2);
    }
}
