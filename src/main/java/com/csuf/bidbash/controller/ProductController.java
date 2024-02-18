package com.csuf.bidbash.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.csuf.bidbash.pojos.Product;
import com.csuf.bidbash.pojos.User;
import com.csuf.bidbash.service.AmazonService;
import com.csuf.bidbash.service.ProductService;
import com.csuf.bidbash.service.UserService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private AmazonService amazonService;

	@PostMapping("/new-item")
	public ResponseEntity<Object> storeNewItem(@RequestPart("product") Product p, @RequestPart("file") List<MultipartFile> file) {

		User u = userService.getUserById(p.getOwnerId());
		if (Objects.isNull(u)) {
			return new ResponseEntity<Object>("User with id:" + p.getOwnerId() + " not found", HttpStatus.NOT_FOUND);
		}
		int pId = productService.getNextProductId();
		p.setProductId(pId);
		
		amazonService.uploadFiles(file);

		Product newProduct = productService.storeNewProduct(p);

		return new ResponseEntity<Object>(newProduct, HttpStatus.OK);
	}

}
