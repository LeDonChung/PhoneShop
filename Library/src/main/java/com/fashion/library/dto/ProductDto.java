package com.fashion.library.dto;

import com.fashion.library.entity.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;

    private String productName;

    private String description;

    private Double price;

    private String image;

    private CategoryEntity category;

    private boolean is_activated;

    private boolean is_deleted;
}
