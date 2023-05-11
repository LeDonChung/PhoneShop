package com.fashion.library.dto;

import com.fashion.library.entity.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;

    private String productName;

    private String description;

    private Double costPrice;
    private Double salePrice;


    private String image;

    private CategoryEntity category;

    private int quantity;

    private boolean is_activated;

    private boolean is_deleted;

    private BrandEntity brand;

    private Collection<ColorEntity> colors;

    private Collection<MemoryEntity> memories;

    private Collection<StorageEntity> storages;
}
