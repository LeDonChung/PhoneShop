package com.phone.library.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.phone.library.entity.ProductEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;

    private String fullName;

    private Date commentDate;

    private String phoneNumber;

    private String email;

    private String content;

    private int stars;

    @JsonIgnore
    private ProductEntity product;
}
