package com.phone.library.service;

import com.phone.library.dto.ColorDto;
import com.phone.library.dto.StorageDto;
import com.phone.library.dto.StoreDto;

import java.util.List;

public interface StoreService {
    StoreDto updateStore(StoreDto storeDto);
    boolean delete(Long id);
    StoreDto findById(Long id);
    List<StoreDto> findAll();
    StoreDto save(StoreDto dto);
    int getQuantityByProductIdAndStorageCodeAndColorCode(Long productId, String storageCode, String colorCode);
    StoreDto findByKeyword(String keyword);
    List<StoreDto> findByProductId(Long productId);
    List<StorageDto> findStoragesByProductId(Long id);
    List<ColorDto> findColorsByStorageCodeAndProductId(String storageCode, Long productId);
    Double getPriceByProductIdAndColorCodeAndStorageCode(Long productId, String colorCode, String storageCode);
}
