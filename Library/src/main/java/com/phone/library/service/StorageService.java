package com.phone.library.service;

import com.phone.library.entity.StorageEntity;

import java.util.List;

public interface StorageService {
    List<StorageEntity> findAll();
}
