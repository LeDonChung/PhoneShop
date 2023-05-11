package com.fashion.library.service.impl;

import com.fashion.library.entity.BrandEntity;
import com.fashion.library.repository.BrandRepository;
import com.fashion.library.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;
    @Override
    public List<BrandEntity> findAll() {
        return brandRepository.findAll();
    }
}
