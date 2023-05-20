package com.phone.library.dto;

import com.phone.library.entity.ProductEntity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class BrandDto {
    private Long id;
    private String name;
    private String code;
    private String logo;

    private boolean is_activated;
    private boolean is_deleted;
    private Long numberOfProduct;

    public BrandDto(Long id, String name, String code, String logo, Long numberOfProduct) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.logo = logo;
        this.numberOfProduct = numberOfProduct;
    }
}
