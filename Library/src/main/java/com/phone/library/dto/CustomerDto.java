package com.phone.library.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String phone;

    private String username;

    private String password;

    private String image;

    private String email;

    private boolean gender;

    private Date birthDate;

    private int status;
}
