package com.fashion.library.repository;

import com.fashion.library.entity.BrandEntity;
import com.fashion.library.entity.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<ColorEntity, Long> {
    ColorEntity finByCode(String code);

}
