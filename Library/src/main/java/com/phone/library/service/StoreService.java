package com.phone.library.service;

import com.phone.library.dto.ColorDto;
import com.phone.library.dto.MemoryDto;
import com.phone.library.dto.StorageDto;
import com.phone.library.dto.StoreDto;
import com.phone.library.entity.ColorEntity;

import java.util.List;

public interface StoreService {
    List<StoreDto> findAll();
    StoreDto save(StoreDto dto);
    StoreDto findByKeyword(String keyword);
    List<StoreDto> findByProductId(Long productId);
    List<StorageDto> findStoragesByProductId(Long id);
    List<ColorDto> findColorsByStorageCodeAndProductId(String storageCode, Long productId);
    Double getPriceByProductIdAndColorCodeAndStorageCode(Long productId, String colorCode, String storageCode);
}