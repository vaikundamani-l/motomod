package com.shop.motomod.product.dto;
import jakarta.validation.constraints.*;

public record CategoryRequestDTO(

    @NotBlank(message = "Category name is required")
    @Size(max = 100, message = "Category name must be at most 100 characters")
    String name
    
) {}
