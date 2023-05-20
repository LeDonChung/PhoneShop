package com.phone.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
public class ProductFilter {
    private String category = "all";
    private String brand = "all";
    private Integer pageNo = 0;
    private int limit = 9;
    private int totalPage = 9;
    private String sortBy = null;
    private String sortType = null;

    public ProductFilter() {
        String category = "all";
        String brand = "all";
        Integer pageNo = 0;
        limit = 9;
        totalPage = 9;
        sortType = "productName";
        sortBy = "asc";
    }

    public ProductFilter(Integer pageNo, String category, String brand, String sortBy, String sortType) {
        if (pageNo != null) {
            this.pageNo = pageNo;
        }
        if (category != null) {
            this.category = category;
        }
        if (brand != null) {
            this.brand = brand;
        }
        if (sortBy != null) {
            this.sortBy = sortBy;
        }
        if (sortType != null) {
            this.sortType = sortType;
        }

    }
}
