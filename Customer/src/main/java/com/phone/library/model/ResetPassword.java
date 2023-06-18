package com.phone.library.model;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPassword {
    @Size(min = 6, message ="The password must be at least 6 characters.")
    private String password;

    private String repeatPassword;
}
