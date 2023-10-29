package com.jhola.security.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/v1/resource")
public class ResourceController {


//	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/one")
    public ResponseEntity<String> resourceOne() {
		System.out.println("ROLE_ADMIN");
    	return new ResponseEntity<String>("ok", HttpStatus.OK);
    }
    
    

    @GetMapping("/two")
    @PreAuthorize("hasRole('QUOTE_ADMIN')")
    public ResponseEntity<String> resourceTwo() {
    	System.out.println("ROLE_SUPER_ADMIN");
    	return new ResponseEntity<String>("ok", HttpStatus.OK);
    }
}
