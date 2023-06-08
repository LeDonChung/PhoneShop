package com.phone.library.repository;

import com.phone.library.entity.MemoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoryRepository extends JpaRepository<MemoryEntity, Long> {
    MemoryEntity findByCode(String code);

}
