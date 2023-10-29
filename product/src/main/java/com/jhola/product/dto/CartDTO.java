package com.jhola.product.dto;

import java.util.ArrayList;
import java.util.List;


public class CartDTO {

	private Long cartId;
	private List<Long> items = new ArrayList<>();

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public List<Long> getItems() {
		return items;
	}

	public void setItems(List<Long> items) {
		this.items = items;
	}


	
	
}
