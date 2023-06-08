package com.phone.library.repository;

import com.phone.library.entity.StorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageRepository extends JpaRepository<StorageEntity, Long> {
    StorageEntity findByCode(String code);
}
