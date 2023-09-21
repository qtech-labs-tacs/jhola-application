package com.jhola.product.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jhola.product.dto.Categories;
import com.jhola.product.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>{


	public List<Product> findProductByCategory(Categories category);

	
	
    	
	
	
	
	
	
	
}
