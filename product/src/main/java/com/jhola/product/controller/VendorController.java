package com.jhola.product.controller;


import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jhola.product.dto.VendorDTO;

@RestController
@RequestMapping("/api/v1/vendor")
public class VendorController {

	private static final Logger log = LoggerFactory.getLogger(VendorController.class);


	private static List<VendorDTO> vendors = new LinkedList<>();
	
	@PostMapping("")
	public ResponseEntity<Boolean> createVendor(@RequestBody VendorDTO vendorDTO) {

		vendors.add(vendorDTO);
		return new ResponseEntity<>(true, HttpStatus.CREATED);

	}



}
