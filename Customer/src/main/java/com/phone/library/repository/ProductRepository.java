package com.phone.library.repository;

import com.phone.library.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    // Admin
    @Query(value="select p from ProductEntity  p where p.is_deleted = false and p.is_activated = true")
    List<ProductEntity> getAll();


    // Customer
    @Query(value = "select * from products\n" +
            "where product_id in\n" +
            "(\n" +
            "\tselect p.product_id from (select * from products where is_deleted = false and is_activated = true) as p\n" +
            "\tjoin stores as s on p.product_id = s.product_id\n" +
            "\tgroup by p.product_id\n" +
            "\thaving count(s.quantity) != 0\n" +
            ")\n", nativeQuery = true)
    List<ProductEntity> getAllProductInStores();
    @Query(value = "select * from products p where p.is_deleted = false and p.is_activated = true order by rand() asc limit 8 ", nativeQuery = true)
    List<ProductEntity> getFeaturedProducts();

    @Query(value = "select * from products p where p.is_deleted = false and p.is_activated = true and p.sale_price != 0 and p.sale_price < p.cost_price order by p.sale_price asc limit 8 ", nativeQuery = true)
    List<ProductEntity> getOfferProducts();

    @Query(value = "select p from ProductEntity p where p.category.categoryCode = ?1 and p.brand.code = ?2 and p.is_deleted = false and p.is_activated = true")
    List<ProductEntity> filterCategoryCodeAndBrandCode(String category, String brand);

    @Query(value = "select p from ProductEntity p where p.category.categoryCode = ?1 and p.is_deleted = false and p.is_activated = true")
    List<ProductEntity> filterCategoryCode(String category);

    @Query(value = "select p from ProductEntity p where p.brand.code = ?1")
    List<ProductEntity> filterBrandCode(String brand);

    @Query(value = "select p from ProductEntity p where p.category.categoryCode = ?1 and p.is_deleted = false and p.is_activated = true order by rand() asc limit 6")
    List<ProductEntity> findAlsoLike(String categoryCode);
}
