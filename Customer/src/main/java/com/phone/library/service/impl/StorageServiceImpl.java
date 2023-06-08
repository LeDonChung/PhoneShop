package com.phone.library.service.impl;

import com.phone.library.entity.StorageEntity;
import com.phone.library.repository.StorageRepository;
import com.phone.library.service.StorageService;
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
