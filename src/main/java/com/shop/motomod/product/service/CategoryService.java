package com.shop.motomod.product.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shop.motomod.product.dto.CategoryRequestDTO;
import com.shop.motomod.product.dto.CategoryResponseDTO;
import com.shop.motomod.product.entity.Categories;
import com.shop.motomod.product.repository.CategoriesRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoriesRepository categoriesRepository;

	// create category
	public String createCategory(CategoryRequestDTO dto) {
		String categoryName = dto.name().trim().toUpperCase();

		if (!categoriesRepository.findByName(categoryName).isEmpty()) {
			System.out.println(categoriesRepository.findByName(categoryName));
			return "Category Altready Exist.";
		}

		Categories category = Categories.builder().name(categoryName).build();

		categoriesRepository.save(category);

		return "Category Added Successfully..!";
	}

	// get all categories
	public List<CategoryResponseDTO> getAllCategories() {

		return categoriesRepository.findAll().stream().map(cat -> new CategoryResponseDTO(cat.getId(), cat.getName()))
				.collect(Collectors.toList());
	}

	// select category by id
	public CategoryResponseDTO getCategoryById(Long id) {

		Categories category = categoriesRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Category not found with id " + id));

		return new CategoryResponseDTO(category.getId(), category.getName());
		
	}

	// update category
	public String updateCategory(Long id, CategoryRequestDTO dto) {
		String categoryName = dto.name().trim().toUpperCase();

		Categories category = categoriesRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Category not found with id " + id));

		category.setName(categoryName);
		categoriesRepository.save(category);

		return "Category Updated Success..!";
	}

	// delete category
	public String deleteCategory(Long id) {

		categoriesRepository.deleteById(id);
		return "Category Deleted..!";
	}

}
