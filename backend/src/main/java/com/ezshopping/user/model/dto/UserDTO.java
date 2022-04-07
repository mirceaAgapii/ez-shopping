package com.ezshopping.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String id;

    @NotNull
    @Size(min = 8, max = 32)
    private String username;

    @NotNull
    private String password;

    @NotNull
    @Email(message = "Wrong email format")
    private String email;

    private String role;
}
