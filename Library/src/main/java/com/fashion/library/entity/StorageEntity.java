package com.fashion.library.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "storages")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StorageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storage_id")
    private Long id;

    private String name;

    private String code;
}

