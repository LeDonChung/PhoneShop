package com.fashion.library.service;

import com.fashion.library.entity.MemoryEntity;
import com.fashion.library.entity.StorageEntity;

import java.util.List;

public interface StorageService {
    List<StorageEntity> findAll();
}
