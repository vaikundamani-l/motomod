package com.shop.motomod.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.motomod.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	boolean existsByNameAndCategoryId(String name, Long categoryId);

}
