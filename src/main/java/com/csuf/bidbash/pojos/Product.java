package com.csuf.bidbash.pojos;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "product_db")
@Entity
public class Product {
	
	@Id
	@Column(name = "product_id")
	private int productId;
	
	@Column(name="owner_id")
	private int ownerId;
	
	@Column(name="product_title")
	private String productTitle;
	
	@Column(name="product_desc")
	private String productDesc;
	
	@Column(name="price")
	private int price;
	
	@Column(name="product_deadline")
	private LocalDateTime productDeadline;
	
	@Column(name="media")
	private String media;
	
	@Column(name="is_available")
	private int isAvailable;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public String getProductTitle() {
		return productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public LocalDateTime getProductDeadline() {
		return productDeadline;
	}

	public void setProductDeadline(LocalDateTime productDeadline) {
		this.productDeadline = productDeadline;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public int getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(int isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", ownerId=" + ownerId + ", productTitle=" + productTitle
				+ ", productDesc=" + productDesc + ", price=" + price + ", productDeadline=" + productDeadline
				+ ", media=" + media + ", isAvailable=" + isAvailable + "]";
	}

	
}
