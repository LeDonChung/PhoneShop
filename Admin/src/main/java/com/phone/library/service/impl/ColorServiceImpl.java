package com.phone.library.service.impl;

import com.phone.library.entity.ColorEntity;
import com.phone.library.repository.ColorRepository;
import com.phone.library.service.ColorService;
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
