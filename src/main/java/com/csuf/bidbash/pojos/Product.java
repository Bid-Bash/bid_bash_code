package com.csuf.bidbash.pojos;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Table(name = "product_db")
@Entity
public class Product {

	@Id
	@Column(name = "product_id")
	private int productId;

	@Column(name = "owner_id")
	private int ownerId;

	@Column(name = "product_title")
	private String productTitle;

	@Column(name = "product_desc")
	private String productDesc;

	@Column(name = "price")
	private double price;

	@Column(name = "product_deadline")
	private LocalDateTime productDeadline;

	@Column(name = "is_available")
	private int isAvailable;
	
	@Column(name="current_bid")
	private double current_bid;
	
	@Transient
	private List<String> fileUrls;

	public List<String> getFileUrls() {
		return fileUrls;
	}

	public void setFileUrls(List<String> fileUrls) {
		this.fileUrls = fileUrls;
	}

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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public LocalDateTime getProductDeadline() {
		return productDeadline;
	}

	public void setProductDeadline(LocalDateTime productDeadline) {
		this.productDeadline = productDeadline;
	}

	public int getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(int isAvailable) {
		this.isAvailable = isAvailable;
	}

	public double getCurrent_bid() {
		return current_bid;
	}

	public void setCurrent_bid(double current_bid) {
		this.current_bid = current_bid;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", ownerId=" + ownerId + ", productTitle=" + productTitle
				+ ", productDesc=" + productDesc + ", price=" + price + ", productDeadline=" + productDeadline
				+ ", isAvailable=" + isAvailable + "]";
	}

}
