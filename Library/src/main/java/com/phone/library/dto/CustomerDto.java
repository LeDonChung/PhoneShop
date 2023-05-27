package com.phone.library.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private Long id;

    @Size(min = 3, max = 15, message ="First name should have 3-15 characters")
    private String firstName;

    @Size(min = 3, max = 15, message ="Last name should have 3-15 characters")
    private String lastName;

    private String phone;

    private String username;

    @Size(min = 6, message ="The password must be at least 6 characters.")
    private String password;

    private String repeatPassword;

    private String image;

    private String email;

    private boolean gender;

    private Date birthDate;

    private String address;

    private int status;
}
