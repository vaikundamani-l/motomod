package com.shop.motomod.product.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.motomod.product.entity.Categories;


public interface CategoriesRepository extends JpaRepository<Categories, Long>{

	Optional<Categories> findByName(String name);

}
