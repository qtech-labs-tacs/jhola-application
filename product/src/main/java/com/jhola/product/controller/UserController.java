package com.jhola.product.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jhola.product.dto.CartDTO;
import com.jhola.product.dto.UserDTO;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);


	public static Map<Long, UserDTO> mapOfUsersByUserID = new HashMap<>();
	
	public static Map<String, UserDTO> mapOfUsersByUsername = new HashMap<>();
	
	public static Long userId = 1l;
	
	@Autowired
	private CartController cartController;
	

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {

		Long id;
		
		if(mapOfUsersByUsername.get(userDTO.getUsername()) != null) {
			return new ResponseEntity<>("Username already registered", HttpStatus.CREATED);
		}

		if (mapOfUsersByUserID.isEmpty()) {
			id = userId;
		}else {
			id = userId + 1;
		}
		
		mapOfUsersByUserID.put(id, userDTO);
		mapOfUsersByUsername.put(userDTO.getUsername(), userDTO);
		
		CartDTO newCart = new CartDTO();
		newCart.setCartId(id);
		cartController.createCart(newCart);
		

		return new ResponseEntity<>("User Registered", HttpStatus.CREATED);

	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
		
		boolean isUserRegistered = false;
		boolean isUserAuthentic = false;
		
		Set<String> keySet = mapOfUsersByUsername.keySet();
		
		for(String username: keySet) {
			if(username.equals(userDTO.getUsername())) {
				isUserRegistered = true;
				UserDTO registeredUser = mapOfUsersByUsername.get(username);
				if (userDTO.getPassword().equals(registeredUser.getPassword())) {
					isUserAuthentic = true;
					break;
				}
			}
		}
		
		if(isUserRegistered && isUserAuthentic) {
			String jwtToken = generateToken();
			return new ResponseEntity<>(jwtToken, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<>("Invalid Username or password", HttpStatus.CREATED);

	}
	
	

	private static String generateToken() {
		UUID randomUUID = UUID.randomUUID();
		String jwtToken = randomUUID.toString();
//			double random = Math.random();
//			String jwtToken = Double.toString(random);
		return jwtToken;
	}

}
