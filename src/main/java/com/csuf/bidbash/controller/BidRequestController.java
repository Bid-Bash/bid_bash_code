package com.csuf.bidbash.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csuf.bidbash.pojos.BidRequest;
import com.csuf.bidbash.pojos.Product;
import com.csuf.bidbash.service.BidRequestService;
import com.csuf.bidbash.service.ProductService;

@RestController
@RequestMapping("/bid-request")
public class BidRequestController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private BidRequestService bidRequestService;
	
	@PostMapping("/bid")
	public ResponseEntity<Object> saveNewBidRequest(@RequestBody BidRequest request){
		int productId = request.getProductId();
		Product p = productService.getProductDetails(productId);
		if(Objects.isNull(p)) {
			return new ResponseEntity<Object>("Product is not found", HttpStatus.NOT_FOUND);
		}else {
			if(p.getOwnerId() == request.getUserId()) {
				return new ResponseEntity<Object>("You cannot bid for your own product", HttpStatus.NOT_ACCEPTABLE);
			}
			
			if(request.getBidAmount() <= p.getCurrent_bid()) {
				return new ResponseEntity<Object>("Bid Value is small than current bid", HttpStatus.NOT_ACCEPTABLE);
			}
			
			int requestId = bidRequestService.getNextBidRequestId();
			request.setRequestId(requestId);
			p.setCurrent_bid(request.getBidAmount());
			
			productService.updateBidAmount(p.getProductId(), p.getCurrent_bid());
			BidRequest newBid=bidRequestService.newBidRequest(request);
			return new ResponseEntity<Object>(newBid, HttpStatus.OK);
		}
		
	}
	
}
