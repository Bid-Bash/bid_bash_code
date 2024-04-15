package com.csuf.bidbash.pojos;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name="auction_sale_db")
@Entity
public class AuctionSale {

	
	@Id
	@Column(name="sale_id")
	private int saleId;
	
	@Column(name="auction_request_id")
	private int auctionRequestId;
	
	@Column(name="transaction_id")
	private String transactionId;
	
	@Column(name="time")
	private LocalDateTime time;

	public int getSaleId() {
		return saleId;
	}

	public void setSaleId(int saleId) {
		this.saleId = saleId;
	}

	public int getAuctionRequestId() {
		return auctionRequestId;
	}

	public void setAuctionRequestId(int auctionRequestId) {
		this.auctionRequestId = auctionRequestId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	
	
	
}
