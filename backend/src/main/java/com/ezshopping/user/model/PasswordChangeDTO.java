package com.ezshopping.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PasswordChangeDTO {
    private String oldPassword;
    private String newPassword;
}
