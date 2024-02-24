package com.csuf.bidbash.pojos;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name="auction_request_db")
@Entity
public class BidRequest {

	@Id
	@Column(name = "request_id")
	private int requestId;
	
	@Column(name="user_id")
	private int userId;
	
	@Column(name="product_id")
	private int productId;
	
	@Column(name="bid_amount")
	private int bidAmount;
	
	@Column(name="time")
	private LocalDateTime time;

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getBidAmount() {
		return bidAmount;
	}

	public void setBidAmount(int bidAmount) {
		this.bidAmount = bidAmount;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	
}
