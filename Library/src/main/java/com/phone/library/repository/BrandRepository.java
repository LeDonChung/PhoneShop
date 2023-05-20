package com.phone.library.repository;

import com.phone.library.dto.BrandDto;
import com.phone.library.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Long> {
    BrandEntity findByCode(String code);

    @Query("select new com.phone.library.dto.BrandDto(b.id, b.name, b.code, b.logo, count(b.id)) from BrandEntity b join ProductEntity p on b.id = p.brand.id group by b.id")
    List<BrandDto> findAllBrandAndProduct();

}
