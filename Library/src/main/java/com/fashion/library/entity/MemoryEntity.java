package com.fashion.library.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "memories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memory_id")
    private Long id;

    private String name;

    private String code;
}
