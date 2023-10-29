package com.jhola.product.dto;

import java.util.List;

public class OrderDTO {
	
	private Long orderId;
	
	private List<ProductDTO> items;
	
	private List<AddressDTO> addresses;
	
	private String userMobileNumber;
	private String userEmailId;
	
	private Long grandTotal;
	private Long grandDiscount;
	
	private boolean orderStatus;
	
	public List<AddressDTO> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressDTO> addresses) {
		this.addresses = addresses;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public List<ProductDTO> getItems() {
		return items;
	}

	public void setItems(List<ProductDTO> items) {
		this.items = items;
	}


	public String getUserMobileNumber() {
		return userMobileNumber;
	}

	public void setUserMobileNumber(String userMobileNumber) {
		this.userMobileNumber = userMobileNumber;
	}

	public String getUserEmailId() {
		return userEmailId;
	}

	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}

	public Long getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(Long grandTotal) {
		this.grandTotal = grandTotal;
	}

	public Long getGrandDiscount() {
		return grandDiscount;
	}

	public void setGrandDiscount(Long grandDiscount) {
		this.grandDiscount = grandDiscount;
	}

	public boolean isOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(boolean orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	
	
	

}
