package com.jhola.product.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jhola.product.dto.AddressDTO;

@RestController
@RequestMapping("/api/v1/address")
public class AddressController {

	public static Map<Long, List<AddressDTO>> mapOfUserAddress = new HashMap<>();

	@PostMapping("/{userId}")
	public ResponseEntity<Boolean> createAddress(@PathVariable Long userId, @RequestBody AddressDTO addressDTO) {
		List<AddressDTO> listOfAddress = mapOfUserAddress.get(userId);

		if (listOfAddress == null) {
			List<AddressDTO> addresses = new ArrayList<>();
			addresses.add(addressDTO);
			mapOfUserAddress.put(userId, addresses);
		} else {
			List<AddressDTO> list = mapOfUserAddress.get(userId);
			list.add(addressDTO);
		}

		return new ResponseEntity<>(true, HttpStatus.CREATED);

	}

	@GetMapping("/{userId}")
	public ResponseEntity<List<AddressDTO>> getAllAddressByUserId(@PathVariable Long userId) {

		List<AddressDTO> address = mapOfUserAddress.get(userId);

		return new ResponseEntity<>(address, HttpStatus.OK);

	}

}
