package com.phone.library.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

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

    private int quantity;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "products_colors", joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "product_id")
            , inverseJoinColumns = @JoinColumn(name = "color_id", referencedColumnName = "color_id"))
    private Collection<ColorEntity> colors;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "products_memories", joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "product_id")
            , inverseJoinColumns = @JoinColumn(name = "memory_id", referencedColumnName = "memory_id"))
    private Collection<MemoryEntity> memories;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "products_storages", joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "product_id")
            , inverseJoinColumns = @JoinColumn(name = "storage_id", referencedColumnName = "storage_id"))
    private Collection<StorageEntity> storages;


    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "brand_id", referencedColumnName = "brand_id")
    private BrandEntity brand;
}
