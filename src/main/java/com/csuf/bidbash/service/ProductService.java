package com.csuf.bidbash.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csuf.bidbash.pojos.Product;
import com.csuf.bidbash.repos.ProductFileRepo;
import com.csuf.bidbash.repos.ProductRepo;

import jakarta.transaction.Transactional;

@Service
public class ProductService {

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private ProductFileRepo pfRepo;

	public Product storeNewProduct(Product p) {
		p.setProductDeadline(LocalDateTime.now());
		p.setIsAvailable(1);
		p.setCurrent_bid(0);
		return productRepo.save(p);
	}
	
	public Product updateProduct(Product p) {
		return productRepo.save(p);
	}

	public int getNextProductId() {
		int id = productRepo.getNextProductId().orElse(0);
		return id + 1;
	}

	public Product getProductDetails(int productId) {
		Product product = productRepo.findById(productId).orElse(null);
		return product;
	}

	public List<String> getFileForProduct(int productId) {
		return pfRepo.getProductFilesByProductId(productId);
	}

	@Transactional
	public void updateBidAmount(int pid, int bidAmount) {
		productRepo.updateBidAmount(pid, bidAmount);
	}

	public List<Object[]> getAllProducts() {
		return productRepo.getAllProductAvailable();
	}
	
	public List<Product> getUserProducts(int userId){
		return productRepo.getAllProductsByOwnerId(userId);
	}
}
