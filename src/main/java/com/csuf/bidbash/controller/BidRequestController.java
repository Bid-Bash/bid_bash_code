package com.csuf.bidbash.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csuf.bidbash.pojos.AuctionSale;
import com.csuf.bidbash.pojos.BidRequest;
import com.csuf.bidbash.pojos.Product;
import com.csuf.bidbash.service.AuctionSaleService;
import com.csuf.bidbash.service.BidRequestService;
import com.csuf.bidbash.service.ProductService;
import com.csuf.bidbash.utils.ProductUtils;

@RestController
@RequestMapping("/bid-request")
@CrossOrigin(origins = "*")
public class BidRequestController {

	@Autowired
	private ProductService productService;

	@Autowired
	private BidRequestService bidRequestService;

	@Autowired
	private AuctionSaleService auctionSaleService;

	@Autowired
	private SimpMessagingTemplate template;

	@PostMapping("/bid")
	public ResponseEntity<Object> saveNewBidRequest(@RequestBody BidRequest request) {
		int productId = request.getProductId();
		Product p = productService.getProductDetails(productId);
		if (Objects.isNull(p)) {
			return new ResponseEntity<Object>("Product is not found", HttpStatus.NOT_FOUND);
		} else {
			if (p.getOwnerId() == request.getUserId()) {
				return new ResponseEntity<Object>("You cannot bid for your own product", HttpStatus.NOT_ACCEPTABLE);
			}
			
			if(p.getIsAvailable() == 0) {
				return new ResponseEntity<Object>("This item is already Sold, you cannot bid!!", HttpStatus.NOT_ACCEPTABLE);
			}

			if (request.getBidAmount() <= p.getCurrent_bid()) {
				return new ResponseEntity<Object>("Bid Value is small than current bid", HttpStatus.NOT_ACCEPTABLE);
			}

			int requestId = bidRequestService.getNextBidRequestId();
			request.setRequestId(requestId);
			p.setCurrent_bid(request.getBidAmount());

			productService.updateBidAmount(p.getProductId(), p.getCurrent_bid());
			BidRequest newBid = bidRequestService.newBidRequest(request);

			template.convertAndSend("/topic/bid/" + newBid.getProductId(), newBid);

			return new ResponseEntity<Object>("Bid Placed Successfully", HttpStatus.OK);
		}

	}

	@PostMapping("/sale")
	public ResponseEntity<Object> saleProduct(@RequestBody ProductUtils product) {
		BidRequest bidRequest = bidRequestService.getBidRequest(product.productId);

		if (Objects.isNull(bidRequest)) {
			return new ResponseEntity<Object>("Bid Request Not Found", HttpStatus.NOT_FOUND);
		}

		// New Sale
		int saleId = auctionSaleService.getNextSaleId();
		AuctionSale auctionSale = new AuctionSale();
		auctionSale.setSaleId(saleId);
		auctionSale.setAuctionRequestId(bidRequest.getRequestId());

		auctionSaleService.sale(auctionSale);

		Product oldProduct = productService.getProductDetails(product.productId);
		if(oldProduct.getIsAvailable() == 0) {
			return new ResponseEntity<Object>("Item is not Available for Sale", HttpStatus.NOT_ACCEPTABLE);
		}
		oldProduct.setIsAvailable(0);
		productService.updateProduct(oldProduct);

		return new ResponseEntity<Object>("Sale Product Successfully", HttpStatus.OK);
	}

	@PostMapping("/bidByUser")
	public ResponseEntity<Object> getBidByUser(@RequestBody ProductUtils utils) {
		int userId = utils.ownerId;
		List<Object> bids = bidRequestService.getAllUserBids(userId);
		return new ResponseEntity<Object>(bids, HttpStatus.OK);

	}

	@PostMapping("/saleOrders")
	public ResponseEntity<Object> getAllSaleTransaction(@RequestBody ProductUtils utils) {
		int userId = utils.userId;
		List<Object> list = auctionSaleService.getSaleTransactionForUser(userId);

		if (Objects.isNull(list)) {
			return new ResponseEntity<Object>("No Pending Or Bought Orders", HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<Object>(list, HttpStatus.OK);
	}

}
