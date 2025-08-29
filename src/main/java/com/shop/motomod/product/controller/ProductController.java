package com.shop.motomod.product.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shop.motomod.product.dto.ProductRequestDTO;
import com.shop.motomod.product.dto.ProductResponseDTO;
import com.shop.motomod.product.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/motomod/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@PostMapping
	public ResponseEntity<String> addProduct(
            @Valid @RequestPart("product") ProductRequestDTO product,
            @RequestPart("images") MultipartFile[] imageFiles) {
        return ResponseEntity.ok(productService.addProduct(product, imageFiles));
    }

	@GetMapping
	public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
       return ResponseEntity.ok(productService.getAllProducts());
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id){
		return ResponseEntity.ok(productService.getProductById(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateProduct(
	        @PathVariable Long id,
	        @RequestPart("product") ProductRequestDTO productRequest,
	        @RequestPart(value = "images", required = false) MultipartFile[] images) {

	    String response = productService.updateProduct(id, productRequest, images);
	    return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return ResponseEntity.ok("Product deleted successfully");
	}


}
