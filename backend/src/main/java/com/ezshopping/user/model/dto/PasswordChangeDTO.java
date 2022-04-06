package com.ezshopping.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeDTO {
    private String oldPassword;
    private String newPassword;
}
