package com.fashion.library.service.impl;

import com.fashion.library.dto.ProductDto;
import com.fashion.library.entity.ProductEntity;
import com.fashion.library.mapper.CategoryMapper;
import com.fashion.library.mapper.ProductMapper;
import com.fashion.library.repository.ProductRepository;
import com.fashion.library.service.CategoryService;
import com.fashion.library.service.ProductService;
import com.fashion.library.utils.ImageUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ImageUploadUtils imageUploadUtils;

    @Override
    public List<ProductDto> findAll() {
        List<ProductEntity> listEntity = productRepository.findAll();
        List<ProductDto> listDto = new ArrayList<>();
        for (ProductEntity entity : listEntity) {
            ProductDto dto = productMapper.toDto(entity);
            if (entity.getCategory() != null) {
                dto.setCategory(entity.getCategory());
            }
            listDto.add(dto);
        }
        return listDto;
    }

    @Override
    public ProductDto findById(Long id) {
        ProductEntity entity = productRepository.findById(id).get();
        ProductDto dto = null;
        if (entity != null) {
            dto = productMapper.toDto(entity);
            if (entity.getCategory() != null) {
                dto.setCategory(entity.getCategory());
            }
        }
        return dto;
    }

    @Override
    public ProductDto save(MultipartFile image, ProductDto dto) {
        try {
            ProductEntity entity = productMapper.toEntity(dto);

            // default
            entity.set_deleted(false);
            entity.set_activated(true);
            if (dto.getCategory() != null) {
                entity.setCategory(dto.getCategory());
            }

            if(dto.getBrand() != null) {
                entity.setBrand(dto.getBrand());
            }

            if(dto.getColors().size() != 0) {
                entity.setColors(dto.getColors());
            }

            if(dto.getMemories().size() != 0) {
                entity.setMemories(dto.getMemories());
            }

            if(dto.getStorages().size() != 0) {
                entity.setStorages(dto.getStorages());
            }

            if (image == null) {
                entity.setImage(null);
            } else {
                imageUploadUtils.uploadImage(image);
                entity.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
            }
            dto = productMapper.toDto(productRepository.save(entity));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }

    @Override
    public ProductDto update(MultipartFile image, ProductDto dtoNew) {

        // find
        ProductEntity entityOld = productRepository.findById(dtoNew.getId()).get();
        // config
        ProductEntity entityNew = productMapper.toEntity(entityOld, dtoNew);

        if (dtoNew.getCategory() != null) {
            entityNew.setCategory(dtoNew.getCategory());
        }
        try {
            if (image.getSize() != 0) {
                // exits
                if (!imageUploadUtils.checkExisted(image)) {
                    imageUploadUtils.uploadImage(image);
                }
                entityNew.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //return
        return productMapper.toDto(productRepository.save(entityNew));
    }

    @Override
    public void delete(Long id) {
        ProductEntity entity = productRepository.findById(id).get();
        entity.set_deleted(true);
        entity.set_activated(false);
        productRepository.save(entity);
    }

    @Override
    public void enable(Long id) {
        ProductEntity entity = productRepository.findById(id).get();
        entity.set_deleted(false);
        entity.set_activated(true);
        productRepository.save(entity);
    }
    @Override
    public Page<ProductDto> pageProducts(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        List<ProductEntity> products = productRepository.findAll();

        List<ProductDto> productDTOList = new ArrayList<>();
        // convert
        for (ProductEntity entity : products) {
            ProductDto dto = productMapper.toDto(entity);
            if (entity.getCategory() != null) {
                dto.setCategory(entity.getCategory());
            }
            productDTOList.add(dto);
        }

        Page<ProductDto> productPages = toPage(productDTOList, pageable);
        return productPages;
    }
    public Page toPage(List<ProductDto> list, Pageable pageable) {
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        /*Bắt đầu là từ offset(skip)*/
        int startIndex = (int) pageable.getOffset();
        /*Nếu + lại lớn hơn thì trả về list size ngược lại thì + lại*/
        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size()) ? list.size() : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }
}
