package com.jhola.product.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
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

import com.jhola.product.dto.OrderDTO;
import com.jhola.product.dto.ProductDTO;
import com.jhola.product.dto.UserDTO;


@RestController
@RequestMapping("/api/v1/payment-order")
public class OrderController {

	private static final Logger log = LoggerFactory.getLogger(OrderController.class);

	public static Map<Long, List<OrderDTO>> mapOfOrdersByUserId = new HashMap<>();
	public static Set<OrderDTO> orders = new HashSet<>();
	
	
	@Autowired
	private ProductController productController;
	

	@PostMapping("/user/{userId}")
	public ResponseEntity<Boolean> placeOrder(@PathVariable Long userId, @RequestBody OrderDTO orderDTO) {
		List<OrderDTO> list = mapOfOrdersByUserId.get(userId);
		
		if(list == null) {
			List<OrderDTO> orders = new LinkedList<OrderDTO>();
			orders.add(orderDTO);
			
			mapOfOrdersByUserId.put(userId, orders);
		}else {
			list.add(orderDTO);
		}
		
		
		
		orders.add(orderDTO);
		
		
		return new ResponseEntity<>(true, HttpStatus.CREATED);

	}
	
	
	@GetMapping("/{cartId}/details/user/{userId}")
	public ResponseEntity<OrderDTO> getOrderDetailsFromCart(@PathVariable Long cartId, @PathVariable Long userId) {
		
		UserDTO userDTO = UserController.mapOfUsersByUserID.get(userId);
		
		OrderDTO orderDTO = new OrderDTO();

		List<Long> listOfProductIds = CartController.mapOfCartByCartID.get(cartId).getItems();
		List<ProductDTO> items = productController.products.stream()
				.filter(product -> listOfProductIds.contains(product.getProductId())).collect(Collectors.toList());
		
		
		
		Long grandTotal = 0l;
		for (ProductDTO productDTO : items) {
			grandTotal = grandTotal +productDTO.getPrice();
		}
		
		
		orderDTO.setItems(items);
		orderDTO.setOrderStatus(false);
		
		// Need to fetch these details from user profile
		orderDTO.setUserEmailId(userDTO.getEmailId());
		orderDTO.setUserMobileNumber(userDTO.getMobileNumber());
		orderDTO.setAddresses(userDTO.getAddresses());
		
		orderDTO.setGrandDiscount(90l);
		orderDTO.setGrandTotal(grandTotal);
		
		
		return new ResponseEntity<>(orderDTO, HttpStatus.OK);
		
	}
	
	
	


}
