package com.phone.library.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDto {
    private Long id;

    @NotEmpty(message = "The email field is required.")
    private String fullName;

    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "The email field is required.")
    private String email;

    @NotEmpty(message = "The phone field is required.")
    private String phone;

    @NotEmpty(message = "The subject field is required.")
    private String subject;

    @NotEmpty(message = "The message field is required.")
    private String message;
}
