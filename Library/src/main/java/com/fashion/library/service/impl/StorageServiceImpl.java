package com.fashion.library.service.impl;

import com.fashion.library.entity.MemoryEntity;
import com.fashion.library.entity.StorageEntity;
import com.fashion.library.repository.StorageRepository;
import com.fashion.library.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class StorageServiceImpl implements StorageService {
    @Autowired
    private StorageRepository storageRepository;

    @Override
    public List<StorageEntity> findAll() {
        return storageRepository.findAll();
    }
}
