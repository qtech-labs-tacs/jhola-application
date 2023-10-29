package com.jhola.product.dto;

import java.util.List;

public class VendorDTO {

	private Long vendorId;
	private List<ProductDTO> products;

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public List<ProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}

	

}
