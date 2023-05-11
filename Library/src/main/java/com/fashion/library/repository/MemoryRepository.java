package com.fashion.library.repository;

import com.fashion.library.entity.ColorEntity;
import com.fashion.library.entity.MemoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoryRepository extends JpaRepository<MemoryEntity, Long> {
    MemoryEntity finByCode(String code);

}
