package com.jhola.product.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jhola.product.dto.ProductDTO;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

	private static final Logger log = LoggerFactory.getLogger(ProductController.class);

	public static List<ProductDTO> products = new LinkedList<>();

	@Value("${message}")
	private String message;

	@PostMapping("")
	public ResponseEntity<Boolean> createProduct(@RequestBody ProductDTO productDTO) {
		products.add(productDTO);
		return new ResponseEntity<>(true, HttpStatus.CREATED);

	}

	@PostMapping("/bulk/upload")
	public ResponseEntity<Boolean> bulkProductUpload(@RequestBody List<ProductDTO> productDTO) {
		products.addAll(productDTO);
		return new ResponseEntity<>(true, HttpStatus.CREATED);
	}

	@GetMapping("")
	public ResponseEntity<List<ProductDTO>> getAllProducts() {
		return new ResponseEntity<>(products, HttpStatus.CREATED);

	}

	@GetMapping("/vendor/{vendorId}")
	public ResponseEntity<List<ProductDTO>> getAllProductsByVendorId(@PathVariable Long vendorId) {
		List<ProductDTO> collect = products.stream().filter(product -> product.getVendorId() == vendorId)
				.collect(Collectors.toList());
		return new ResponseEntity<>(collect, HttpStatus.CREATED);

	}

	@GetMapping("/message")
	public ResponseEntity<String> printMessage() {
		log.info(message);
		return new ResponseEntity<>("Success", HttpStatus.OK);

	}

}
