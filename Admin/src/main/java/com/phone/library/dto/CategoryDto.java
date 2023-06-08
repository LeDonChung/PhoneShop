package com.phone.library.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long id;

    @Size(min = 3, max = 15, message = "Invalid name (3-15 characters)")
    private String categoryName;

    @Size(min = 3, max = 15, message = "Invalid code (3-15 characters)")
    private String categoryCode;

    private String description;

    private boolean is_activated;

    private boolean is_deleted;
}
