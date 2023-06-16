package com.phone.library.model;

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
public class CustomerModel {

    private Long id;

    @NotEmpty(message ="First name field is required.")
    private String firstName;

    @NotEmpty(message ="Last name field is required.")
    private String lastName;

    private String phone;

    private String username;

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
