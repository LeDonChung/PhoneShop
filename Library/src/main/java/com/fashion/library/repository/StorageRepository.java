package com.fashion.library.repository;

import com.fashion.library.entity.MemoryEntity;
import com.fashion.library.entity.StorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageRepository extends JpaRepository<StorageEntity, Long> {
    StorageEntity findByCode(String code);
}
