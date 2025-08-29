package com.shop.motomod.product.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.shop.motomod.product.dto.CategoryRequestDTO;
import com.shop.motomod.product.dto.CategoryResponseDTO;
import com.shop.motomod.product.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/motomod/categories")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;

	@PostMapping
	public ResponseEntity<String> createCategory(@Valid @RequestBody CategoryRequestDTO dto) {
		String response = categoryService.createCategory(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping
	public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
		return ResponseEntity.ok(categoryService.getAllCategories());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
	    return ResponseEntity.ok(categoryService.getCategoryById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequestDTO dto) {
		return ResponseEntity.ok(categoryService.updateCategory(id, dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {

		return ResponseEntity.accepted().body(categoryService.deleteCategory(id));
	}
}
