package com.phone.library.repository;

import com.phone.library.dto.MemoryDto;
import com.phone.library.dto.StoreDto;
import com.phone.library.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Long> {
    @Query(value = "select s from StoreEntity s where s.quantity != 0 and s.product.id = ?1")
    List<StoreEntity> findByProductId(Long productId);
    @Query(value = "select distinct s.color from StoreEntity s where s.product.id = ?2 and s.quantity != 0 and s.storage.code = ?1")
    List<ColorEntity> findColorsByStorageCodeAndProductId(String storageCode,Long productId);
    @Query(value = "select distinct s.storage from StoreEntity s where s.product.id = ?1 and s.quantity != 0")
    List<StorageEntity> findStoragesByProductId(Long productId);
    @Query(value = "select s from StoreEntity s where s.product.id = ?1 and s.quantity != 0 and s.color.code = ?2 and s.storage.code = ?3")
    StoreEntity getStoreByProductIdAndColorCodeAndStorageCode(Long productId, String colorCode, String storageCode);
    @Query(value = "select s from StoreEntity s where s.product.id = ?1 and s.color.code = ?2 and s.storage.code = ?3")
    StoreEntity getByProductIdAndColorCodeAndStorageCode(Long productId, String colorCode, String storageCode);
}
