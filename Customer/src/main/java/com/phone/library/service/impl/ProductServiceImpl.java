package com.phone.library.service.impl;

import com.phone.library.dto.CommentDto;
import com.phone.library.dto.ProductDto;
import com.phone.library.entity.CommentEntity;
import com.phone.library.entity.ProductEntity;
import com.phone.library.mapper.CommentMapper;
import com.phone.library.mapper.ProductMapper;
import com.phone.library.model.ProductFilter;
import com.phone.library.repository.ProductRepository;
import com.phone.library.service.ProductService;
import com.phone.library.utils.ImageUploadUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ImageUploadUtils imageUploadUtils;
    private final CommentMapper commentMapper;
    @Override
    public List<ProductDto> findAll() {
        List<ProductEntity> listEntity = productRepository.getAll();
        List<ProductDto> listDto = new ArrayList<>();
        for (ProductEntity entity : listEntity) {
            ProductDto dto = productMapper.toDto(entity);
            listDto.add(dto);
        }
        return listDto;
    }

    @Override
    public List<ProductEntity> findAllProduct() {
        return productRepository.getAll();
    }

    @Override
    public ProductDto findById(Long id) {
        ProductEntity entity = productRepository.findById(id).get();
        ProductDto dto = null;
        if (entity != null) {
            dto = productMapper.toDto(entity);
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

            if (image.getSize() == 0) {
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


    // Customer
    @Override
    public List<ProductDto> findFeaturedProduct() {
        List<ProductDto> listDto = new ArrayList<>();
        List<ProductEntity> listEntity = productRepository.getFeaturedProducts();
        for (ProductEntity entity : listEntity) {
            listDto.add(productMapper.toDto(entity));
        }
        return listDto;
    }

    @Override
    public List<ProductDto> findOfferProducts() {
        List<ProductDto> listDto = new ArrayList<>();
        List<ProductEntity> listEntity = productRepository.getOfferProducts();
        for (ProductEntity entity : listEntity) {
            listDto.add(productMapper.toDto(entity));
        }
        return listDto;
    }

    @Override
    public List<ProductDto> findBySearchKey(String key) {
        List<ProductDto> productDTOList = new ArrayList<>();
        List<ProductEntity> products = productRepository.search(key);
        // convert
        for (ProductEntity entity : products) {
            ProductDto dto = productMapper.toDto(entity);
            productDTOList.add(dto);
        }

        return productDTOList;
    }

    @Override
    public ProductDto addComment(Long productId, CommentDto commentDto) {
        ProductEntity product = productRepository.findById(productId).get();
        if(product == null) {
            return null;
        }

        CommentEntity comment = commentMapper.toEntity(commentDto);
        comment.setProduct(product);
        comment.setCommentDate(new Date());

        List<CommentEntity> comments = product.getComments();
        comments.add(comment);
        product.setComments(comments);

        ProductEntity productNew = productRepository.save(product);
        return productMapper.toDto(productNew);
    }

    @Override
    public Page<ProductDto> getPageProducts(ProductFilter filter) {
        Pageable pageable = PageRequest.of(filter.getPageNo(), filter.getLimit());

        List<ProductEntity> products = filterAll(filter);
        products = sortAll(products, filter);

        List<ProductDto> productDTOList = new ArrayList<>();
        // convert
        for (ProductEntity entity : products) {
            ProductDto dto = productMapper.toDto(entity);
            productDTOList.add(dto);
        }

        Page<ProductDto> productPages = toPage(productDTOList, pageable);
        return productPages;
    }


    public List<ProductEntity> sortAll(List<ProductEntity> entitySort, ProductFilter filter) {
        // null not sort
        if (filter.getSortBy() != null) {
            if (filter.getSortBy().equals("salePrice")) {
                entitySort.sort((o1, o2) -> {
                    if (filter.getSortType().equals("asc")) {
                        return (int) (o1.getSalePrice() - o2.getSalePrice());
                    } else {
                        return (int) (o2.getSalePrice() - o1.getSalePrice());
                    }
                });
            } else if (filter.getSortBy().equals("productName")) {
                entitySort.sort((o1, o2) -> {
                    if (filter.getSortType().equals("asc")) {
                        return o1.getProductName().compareTo(o2.getProductName());
                    } else {
                        return o2.getProductName().compareTo(o1.getProductName());
                    }
                });
            }
        }
        return entitySort;
    }

    public List<ProductEntity> filterAll(ProductFilter filter) {
        List<ProductEntity> entities = null;
        if (!filter.getCategory().equals("all") && !filter.getBrand().equals("all")) {
            entities = productRepository.filterCategoryCodeAndBrandCode(filter.getCategory(), filter.getBrand());
        } else if (filter.getCategory().equals("all") && !filter.getBrand().equals("all")) {
            entities = productRepository.filterBrandCode(filter.getBrand());
        } else if (!filter.getCategory().equals("all") && filter.getBrand().equals("all")) {
            entities = productRepository.filterCategoryCode(filter.getCategory());
        } else {
            entities = productRepository.getAll();
        }
        return entities;
    }

    @Override
    public List<ProductDto> findAlsoLike(String category) {
        List<ProductEntity> entities = productRepository.findAlsoLike(category);
        List<ProductDto> listDto = new ArrayList<>();

        for (ProductEntity entity : entities) {
            listDto.add(productMapper.toDto(entity));
        }

        return listDto;
    }
}
