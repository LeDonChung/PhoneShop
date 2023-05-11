package com.fashion.library.service.impl;

import com.fashion.library.entity.ColorEntity;
import com.fashion.library.repository.ColorRepository;
import com.fashion.library.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class ColorServiceImpl implements ColorService {
    @Autowired
    private ColorRepository colorRepository;
    @Override
    public List<ColorEntity> findAll() {
        return colorRepository.findAll();
    }
}
