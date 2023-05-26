package com.phone.library.dto;

import com.phone.library.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    private int quantity; // have

    private boolean is_activated;

    private boolean is_deleted;

    private BrandEntity brand;

    private MemoryEntity memory;

    private String colorCode;

    private String storageCode;

}
