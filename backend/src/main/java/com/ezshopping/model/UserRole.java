package com.ezshopping.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserRole {

    private String value;

    public static final UserRole CLIENT = create("CLIENT");
    public static final UserRole ADMINISTRATOR = create("ADMINISTRATOR");

    private static UserRole create(String value) {
        return new UserRole(value);
    }
}
