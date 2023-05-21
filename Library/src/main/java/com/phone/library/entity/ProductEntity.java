package com.phone.library.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String productName;
    @Column(columnDefinition = "TEXT")
    private String description;

    private Double costPrice;
    private Double salePrice;

    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private CategoryEntity category;

    private boolean is_activated;

    private boolean is_deleted;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "brand_id", referencedColumnName = "brand_id")
    private BrandEntity brand;
}
