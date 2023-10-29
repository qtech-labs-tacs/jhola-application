package com.jhola.product.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jhola.product.dto.ProductDTO;
import com.jhola.product.dto.WishListDTO;

@RestController
@RequestMapping("/api/v1/wishlist")
public class WishListController {

	private static final Logger log = LoggerFactory.getLogger(WishListController.class);



	@PostMapping("")
	public ResponseEntity<Boolean> createWishList(@RequestBody WishListDTO productDTO) {
		
		return new ResponseEntity<>(true, HttpStatus.CREATED);

	}

	
	@PostMapping("/add")
	public ResponseEntity<Boolean> addToWishList(@RequestBody ProductDTO productDTO) {
		
		return new ResponseEntity<>(true, HttpStatus.CREATED);
		
	}
	


}
