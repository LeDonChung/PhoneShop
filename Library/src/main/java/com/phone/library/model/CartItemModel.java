package com.phone.library.model;

import com.phone.library.dto.ProductDto;
import com.phone.library.entity.ColorEntity;
import com.phone.library.entity.MemoryEntity;
import com.phone.library.entity.ProductEntity;
import com.phone.library.entity.StorageEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemModel {
    private Long id;

    private int quantity;

    private double totalPrice;

    private MemoryEntity memory;

    private ColorEntity color;

    private StorageEntity storage;

    private ProductDto productDto;
}
