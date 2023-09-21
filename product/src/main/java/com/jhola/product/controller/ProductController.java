package com.jhola.product.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jhola.product.dto.Categories;
import com.jhola.product.dto.ProductDTO;
import com.jhola.product.model.Product;
import com.jhola.product.repository.ProductRepository;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Autowired 
	private ProductRepository repository;
	
	
	
	@PostMapping("")
	public ResponseEntity<Long> createProduct(@RequestBody ProductDTO productDTO){
		
		Product product = modelMapper.map(productDTO, Product.class);
		Product savedProduct = repository.save(product);
		return new ResponseEntity<>(savedProduct.getProductId(), HttpStatus.CREATED);
	
	}
	
	
	
	@GetMapping("/{productId}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable Long productId){
		
		Optional<Product> optionalProduct = repository.findById(productId);
		if(optionalProduct.isPresent()) {
			Product product = optionalProduct.get();
			ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
			return new ResponseEntity<>(productDTO, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	
	@GetMapping("/all")
	public ResponseEntity<List<ProductDTO>> getAllProducts(){

		Iterable<Product> iterableProduct = repository.findAll();
		ProductDTO productDTO;
		List<ProductDTO> listOfProductDTO =  new ArrayList<>();
		
		for(Product product: iterableProduct) {
			productDTO = modelMapper.map(product, ProductDTO.class);
			listOfProductDTO.add(productDTO);
		}
		
		return new ResponseEntity<>(listOfProductDTO, HttpStatus.OK);
	}

	
	
	@GetMapping("/filter/{category}")
	public ResponseEntity<List<ProductDTO>> getProductByCategory(@PathVariable String category){
		
		List<Product> listOfProducts = repository.findProductByCategory(Categories.valueOf(category));
		
		List<ProductDTO> listOfProductDTO = new ArrayList<>();
		
		for(Product product: listOfProducts) {
			ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
			listOfProductDTO.add(productDTO);
		}
		
		return new ResponseEntity<>(listOfProductDTO, HttpStatus.OK);

	}
	
	
	
	
	
}
