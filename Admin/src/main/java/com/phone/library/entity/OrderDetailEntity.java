package com.phone.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "order_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long id;

    private int quantity;

    private double totalPrice;

    private double unitPrice;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private OrderEntity order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "memory_id", referencedColumnName = "memory_id")
    private MemoryEntity memory;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "color_id", referencedColumnName = "color_id")
    private ColorEntity color;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "storage_id", referencedColumnName = "storage_id")
    private StorageEntity storage;
}
