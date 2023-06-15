package com.phone.library.dto;

import com.phone.library.entity.ProductEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private Long id;

    @NotEmpty(message ="First name cannot be empty")
    private String firstName;

    @NotEmpty(message ="First name cannot be empty")
    private String lastName;

    private String phone;

    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email cannot be empty")
    private String username;

    @Size(min = 6, message ="The password must be at least 6 characters.")
    private String password;

    private String repeatPassword;

    private String image;

    private String email;

    private boolean gender;

    private String birthDate;

    private String address;

    private int status;
    
    private List<ProductEntity> favorites;

}
