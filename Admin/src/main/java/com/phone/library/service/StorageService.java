package com.phone.library.service;

import com.phone.library.dto.StoreDto;
import com.phone.library.entity.StorageEntity;
import com.phone.library.entity.StoreEntity;

import java.util.List;

public interface StorageService {
    List<StorageEntity> findAll();

}
