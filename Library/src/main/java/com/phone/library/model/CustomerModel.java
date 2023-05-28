package com.phone.library.dto;

import com.phone.library.entity.ProductEntity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerModel {

    private Long id;

    @Size(min = 3, max = 15, message ="First name should have 3-15 characters")
    private String firstName;

    @Size(min = 3, max = 15, message ="Last name should have 3-15 characters")
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
