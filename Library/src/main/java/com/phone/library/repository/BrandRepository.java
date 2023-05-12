package com.phone.library.repository;

import com.phone.library.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Long> {
    BrandEntity findByCode(String code);
}
