package com.csuf.bidbash.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csuf.bidbash.pojos.AuctionSale;
import com.csuf.bidbash.repos.AuctionSaleRepo;

import jakarta.transaction.Transactional;

@Service
public class AuctionSaleService {

	@Autowired
	private AuctionSaleRepo saleRepo;

	public AuctionSale sale(AuctionSale sale) {
		sale.setTime(LocalDateTime.now());
		return saleRepo.save(sale);
	}

	public int getNextSaleId() {
		return saleRepo.nextAuctionSaleId().orElse(0) + 1;
	}
	
	public List<Object> getSaleTransactionForUser(int userId) {
		return saleRepo.getUserSaleOrders(userId);
	}
	
	@Transactional
	public void updateTransactionId(int auctionRequestId, String transactionId) {
		saleRepo.findByAuctionRequestId(auctionRequestId, transactionId);
	}
	
	

}
