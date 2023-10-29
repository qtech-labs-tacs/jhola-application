package com.jhola.product.dto;

import java.util.List;

public class WishListDTO {

	private Long wishListId;
	private List<ProductDTO> listOfProducts;

	public Long getWishListId() {
		return wishListId;
	}

	public void setWishListId(Long wishListId) {
		this.wishListId = wishListId;
	}

	public List<ProductDTO> getListOfProducts() {
		return listOfProducts;
	}

	public void setListOfProducts(List<ProductDTO> listOfProducts) {
		this.listOfProducts = listOfProducts;
	}

}
