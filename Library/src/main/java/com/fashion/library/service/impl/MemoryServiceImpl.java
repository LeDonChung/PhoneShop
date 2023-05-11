package com.fashion.library.service.impl;

import com.fashion.library.entity.MemoryEntity;
import com.fashion.library.repository.MemoryRepository;
import com.fashion.library.service.MemoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MemoryServiceImpl implements MemoryService {
    @Autowired
    private MemoryRepository memoryRepository;
    @Override
    public List<MemoryEntity> findAll() {
        return memoryRepository.findAll();
    }
}
