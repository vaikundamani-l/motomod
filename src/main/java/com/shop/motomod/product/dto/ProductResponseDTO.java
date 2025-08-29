package com.shop.motomod.product.dto;

import java.util.List;

public record ProductResponseDTO(
	    Long id,
	    String name,
	    Double price,
	    int stockQuantity,
	    Long categoryId,
	    String categoryName,
	    String description,
	    List<ImageDTO> images
	) {}
