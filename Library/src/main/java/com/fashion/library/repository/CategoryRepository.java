package com.fashion.library.repository;

import com.fashion.library.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    CategoryEntity findByCategoryCode(String categoryCode);
    @Query("select c from CategoryEntity  c where c.is_activated = true and c.is_deleted = false")
    List<CategoryEntity> findByActivated();
}
