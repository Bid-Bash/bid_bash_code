package com.csuf.bidbash.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csuf.bidbash.pojos.Product;
import com.csuf.bidbash.repos.ProductRepo;

@Service
public class ProductService {

	
	@Autowired
	private ProductRepo productRepo;
	
	public Product storeNewProduct(Product p) {
		p.setProductDeadline(LocalDateTime.now());
		p.setIsAvailable(1);
		return productRepo.save(p);
	}
	
	public int getNextProductId() {
		int id = productRepo.getNextProductId().orElse(0);
		return id +1;
	}
}
