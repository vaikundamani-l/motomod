package com.shop.motomod.product.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shop.motomod.product.dto.ImageDTO;
import com.shop.motomod.product.dto.ProductRequestDTO;
import com.shop.motomod.product.dto.ProductResponseDTO;
import com.shop.motomod.product.entity.Categories;
import com.shop.motomod.product.entity.Image;
import com.shop.motomod.product.entity.Product;
import com.shop.motomod.product.repository.CategoriesRepository;
import com.shop.motomod.product.repository.ImageRepository;
import com.shop.motomod.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final ImageRepository imageRepository;
	private final CategoriesRepository categoriesRepository;
	private final ClaudinaryService cloudinaryService;

	// this method is used to add a product
	public String addProduct(ProductRequestDTO product, MultipartFile[] imageFile) {

		if (productRepository.existsByNameAndCategoryId(product.name(), product.categoryId())) {
			return "Product already exists in this category!";
		}

		Product newProduct = Product.builder().name(product.name()).price(product.price())
				.stockQuantity(product.stockQuantity()).description(product.description())
				.categoryId(product.categoryId()).build();
		productRepository.save(newProduct);

		uploadImage(newProduct.getId(), imageFile);

		return "Product added successfully";
	}
	

	// this method is used to get all products
	public List<ProductResponseDTO> getAllProducts() {
		List<Product> products = productRepository.findAll();

		return products.stream().map(product -> {
			List<ImageDTO> imageDtos = imageRepository.findByProductId(product.getId()).stream()
					.map(image -> new ImageDTO(image.getImageId(), image.getImageUrl(), image.getImagePublicId()))
					.collect(Collectors.toList());

			String categoryName = categoriesRepository.findById(product.getCategoryId())
					.map(category -> category.getName()).orElse("Unknown");

			return new ProductResponseDTO(
				    product.getId(),              
				    product.getName(),            
				    product.getPrice(),           
				    product.getStockQuantity(),   
				    product.getCategoryId(),     
				    categoryName,                
				    product.getDescription(),     
				    imageDtos                     
				);


		}).collect(Collectors.toList());
	}

	//this method update products and images
	public String updateProduct(Long productId, ProductRequestDTO productRequest, MultipartFile[] imageFile) {
		Product existingProduct = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found with id " + productId));

		existingProduct.setName(productRequest.name());
		existingProduct.setPrice(productRequest.price());
		existingProduct.setStockQuantity(productRequest.stockQuantity());
		existingProduct.setDescription(productRequest.description());
		existingProduct.setCategoryId(productRequest.categoryId());
		productRepository.save(existingProduct);

		if (imageFile != null && imageFile.length > 0) {
			deleteImage(productId);
			uploadImage(productId,imageFile);
		}

		return "Product Successfully Updated..!";
	}

	// this method is used to delete a product
	public void deleteProduct(Long productId) {
		deleteImage(productId);
		productRepository.deleteById(productId);

	}

	// upload images into cloudinary and DB
	private void uploadImage(Long productId, MultipartFile[] imageFile) {
		for (MultipartFile file : imageFile) {
			try {
				Map<String, String> response = cloudinaryService.uploadFile(file, "products");

				Image newImage = Image.builder().productId(productId).imageUrl(response.get("url"))
						.imagePublicId(response.get("public_id")).build();

				imageRepository.save(newImage);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	//this method delete images from db 
	private void deleteImage(Long productId) {
		List<Image> images = imageRepository.findByProductId(productId);
		imageRepository.deleteAll(images);

		for (Image image : images) {
			try {
				cloudinaryService.deleteFile(image.getImagePublicId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	public ProductResponseDTO getProductById(Long productId) {
	    Product product = productRepository.findById(productId)
	            .orElseThrow(() -> new RuntimeException("Product not found with id " + productId));

	    String categoryName = categoriesRepository.findById(product.getCategoryId())
	            .map(Categories::getName)
	            .orElse("Unknown Category");

	    // Convert Image -> ImageDTO using stream
	    List<ImageDTO> imageDTOs = imageRepository.findByProductId(productId)
	            .stream()
	            .map(image -> new ImageDTO(
	                    image.getImageId(),
	                    image.getImageUrl(),
	                    image.getImagePublicId()
	            ))
	            .toList();

	    return new ProductResponseDTO(
	            product.getId(),
	            product.getName(),
	            product.getPrice(),
	            product.getStockQuantity(),
	            product.getCategoryId(),
	            categoryName,
	            product.getDescription(),
	            imageDTOs
	    );

	}



}
