package com.fashion.library.service;

import com.fashion.library.entity.ColorEntity;
import com.fashion.library.entity.MemoryEntity;

import java.util.List;

public interface MemoryService {
    List<MemoryEntity> findAll();
}
