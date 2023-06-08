package com.phone.library.service.impl;

import com.phone.library.entity.MemoryEntity;
import com.phone.library.repository.MemoryRepository;
import com.phone.library.service.MemoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemoryServiceImpl implements MemoryService {
    @Autowired
    private MemoryRepository memoryRepository;
    @Override
    public List<MemoryEntity> findAll() {
        return memoryRepository.findAll();
    }
}
