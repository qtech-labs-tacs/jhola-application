package com.jhola.product.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jhola.product.dto.CartDTO;
import com.jhola.product.dto.ProductDTO;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

	private static final Logger log = LoggerFactory.getLogger(CartController.class);

	private static Set<CartDTO> carts = new HashSet<>();
	public static Map<Long, CartDTO> mapOfCartByCartID = new HashMap<>();

	@Autowired
	private ProductController productController;

	@PostMapping("")
	public ResponseEntity<Boolean> createCart(@RequestBody CartDTO cartDTO) {

		carts.add(cartDTO);
		mapOfCartByCartID.put(cartDTO.getCartId(), cartDTO);

		return new ResponseEntity<>(true, HttpStatus.CREATED);

	}

	@PostMapping("/{cartId}/{productId}")
	public ResponseEntity<Boolean> addToCart(@PathVariable Long cartId, @PathVariable Long productId) {

		CartDTO cartDTO = mapOfCartByCartID.get(cartId);
		List<Long> items = cartDTO.getItems();
		items.add(productId);

		return new ResponseEntity<>(true, HttpStatus.CREATED);

	}

	@GetMapping("/{cartId}/all")
	public ResponseEntity<List<ProductDTO>> getAllProductByCartId(@PathVariable Long cartId) {

		CartDTO cartDTO = mapOfCartByCartID.get(cartId);
		
		// 1, 3
		List<Long> listOfProductIds = cartDTO.getItems();
								   // 1..10	
		List<ProductDTO> listOfProductInCart = productController.products.stream()
				.filter(product -> listOfProductIds.contains(product.getProductId())).collect(Collectors.toList());

		
		return new ResponseEntity<>(listOfProductInCart, HttpStatus.OK);

	}

}
