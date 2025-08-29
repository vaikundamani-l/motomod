package com.shop.motomod.product.dto;

import jakarta.validation.constraints.*;

public record ProductRequestDTO(

    @NotBlank(message = "Product name is required")
    @Size(max = 200, message = "Name must be at most 200 characters")
    String name,

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    Double price,

    @Size(max = 1000, message = "Description must be at most 1000 characters")
    String description,

    @Min(value = 0, message = "Stock must be at least 0")
    int stockQuantity,

    @NotNull(message = "Category ID is required")
    Long categoryId
) {}
