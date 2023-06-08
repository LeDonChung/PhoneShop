package com.phone.library.service.impl;

import com.phone.library.dto.BrandDto;
import com.phone.library.dto.CategoryDto;
import com.phone.library.entity.BrandEntity;
import com.phone.library.entity.CategoryEntity;
import com.phone.library.mapper.BrandMapper;
import com.phone.library.repository.BrandRepository;
import com.phone.library.service.BrandService;
import com.phone.library.utils.ImageUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private ImageUploadUtils imageUploadUtils;

    @Override
    public List<BrandDto> findAll() {
        List<BrandEntity> entityList = brandRepository.findAll();
        return convertListBrandDto(entityList);
    }

    private List<BrandDto> convertListBrandDto(List<BrandEntity> entityList) {
        if (entityList == null) {
            entityList = new ArrayList<>();
        }

        List<BrandDto> dtoList = new ArrayList<>();
        for (BrandEntity entity : entityList) {
            dtoList.add(brandMapper.toDto(entity));
        }
        return dtoList;
    }

    @Override
    public BrandDto save(BrandDto brandDto , MultipartFile logoBrand) {
        try {
            // config
            BrandEntity entity = brandMapper.toEntity(brandDto);
            entity.set_deleted(false);
            entity.set_activated(true);

            if (logoBrand == null) {
                entity.setLogo(null);
            } else {
                imageUploadUtils.uploadLogo(logoBrand);
                entity.setLogo(Base64.getEncoder().encodeToString(logoBrand.getBytes()));
            }

            // save
            BrandEntity entityNew = brandRepository.save(entity);

            // return
            brandDto = brandMapper.toDto(entityNew);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return brandDto;
    }

    @Override
    public BrandDto update(BrandDto brandDto , MultipartFile logoBrand) {
        // find
        BrandEntity entityOld = brandRepository.findById(brandDto.getId()).get();
        // config
        BrandEntity entityNew = brandMapper.toEntity(entityOld, brandDto);
        try {
            if (logoBrand != null) {
                // exits
                if (!imageUploadUtils.checkLogoExisted(logoBrand)) {
                    imageUploadUtils.uploadLogo(logoBrand);
                }
                entityNew.setLogo(Base64.getEncoder().encodeToString(logoBrand.getBytes()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return
        return brandMapper.toDto(brandRepository.save(entityNew));
    }

    @Override
    public void delete(String code) {
        BrandEntity entity = brandRepository.findByCode(code);
        entity.set_deleted(true);
        entity.set_activated(false);
        brandRepository.save(entity);
    }

    @Override
    public void enable(String code) {
        BrandEntity entity = brandRepository.findByCode(code);
        entity.set_deleted(false);
        entity.set_activated(true);
        brandRepository.save(entity);
    }

    @Override
    public List<BrandDto> findAllBrandAndProduct() {
        return brandRepository.findAllBrandAndProduct();
    }

    @Override
    public BrandDto findByCode(String code) {
        return brandMapper.toDto(brandRepository.findByCode(code));
    }
}
