package com.phone.library.repository;

import com.phone.library.entity.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<ColorEntity, Long> {
    ColorEntity findByCode(String code);

}
