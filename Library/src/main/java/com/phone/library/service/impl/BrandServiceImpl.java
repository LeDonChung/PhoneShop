package com.phone.library.service.impl;

import com.phone.library.entity.BrandEntity;
import com.phone.library.repository.BrandRepository;
import com.phone.library.service.BrandService;
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
