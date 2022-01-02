package com.ezshopping.user.model;

import lombok.Getter;

@Getter
public class PasswordChangeDTO {
    private String oldPassword;
    private String newPassword;
}
