package com.ezshopping.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDTO {
    private String id;
    private String username;
    private String password;
    private String email;
}
