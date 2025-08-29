package com.shop.motomod.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.motomod.product.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

	List<Image> findByProductId(Long productId);
	
}
