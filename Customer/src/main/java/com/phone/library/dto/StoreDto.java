package com.phone.library.dto;

import com.phone.library.entity.ColorEntity;
import com.phone.library.entity.ProductEntity;
import com.phone.library.entity.StorageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreDto {
    private Long id;

    private Double salePrice;

    private Double costPrice;

    private int quantity;

    private ProductEntity product;

    private ColorEntity color;

    private StorageEntity storage;
}
