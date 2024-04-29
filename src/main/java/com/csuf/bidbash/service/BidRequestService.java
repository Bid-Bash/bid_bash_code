package com.csuf.bidbash.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csuf.bidbash.pojos.BidRequest;
import com.csuf.bidbash.repos.BidRequestRepo;

@Service
public class BidRequestService {

	@Autowired
	private BidRequestRepo bidRequestRepo;

	public BidRequest newBidRequest(BidRequest request) {
		request.setTime(LocalDateTime.now());
		return bidRequestRepo.save(request);
	}

	public int getNextBidRequestId() {

		int id = bidRequestRepo.nextBidRequestId().orElse(0);
		return id + 1;

	}
	
	public BidRequest getBidRequest(int productId) {
		return bidRequestRepo.getBidRequest(productId);
	}
	
	public List<Object> getAllUserBids(int userId){
		return bidRequestRepo.getUserBids(userId);
	}
	
	
	
	

}
