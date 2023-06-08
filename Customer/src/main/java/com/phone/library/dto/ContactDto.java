package com.phone.library.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDto {
    private Long id;

    @Size(min = 3, message = "The full name must be at least 3 characters.")
    private String fullName;

    @Size(min = 1, message = "The email field is required.")
    private String email;

    @Size(min = 1, message = "The phone field is required.")
    private String phone;

    @Size(min = 6, message = "The subject must be at least 6 characters.")
    private String subject;

    @Size(min = 1, message = "The message field is required.")
    private String message;
}
