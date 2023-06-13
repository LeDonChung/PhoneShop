package com.phone.library.dto;

import com.phone.library.entity.BrandEntity;
import com.phone.library.entity.CategoryEntity;
import com.phone.library.entity.CommentEntity;
import com.phone.library.entity.MemoryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private boolean is_activated;

    private boolean is_deleted;

    private BrandEntity brand;

    private MemoryEntity memory;

    private String colorCode;

    private String storageCode;

    private List<CommentEntity> comments;

}
