package com.csuf.bidbash.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.csuf.bidbash.pojos.Product;
import com.csuf.bidbash.pojos.User;
import com.csuf.bidbash.service.AzureService;
import com.csuf.bidbash.service.ProductService;
import com.csuf.bidbash.service.UserService;
import com.csuf.bidbash.utils.ProductUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	@Autowired
	private AzureService azureService;

	@RequestMapping(value = "/new-item", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE })
	public ResponseEntity<Object> storeNewItem(@RequestParam(value = "file", required = false) MultipartFile[] file,
			@RequestParam("product") String p1) {

		ObjectMapper mapper = new ObjectMapper();
		Product p;
		try {
			p = mapper.readValue(p1, Product.class);
		} catch (JsonProcessingException e1) {
			return new ResponseEntity<Object>("Product Is InCorrect", HttpStatus.BAD_REQUEST);
		}

		User u = userService.getUserById(p.getOwnerId());
		if (Objects.isNull(u)) {
			return new ResponseEntity<Object>("User with id:" + p.getOwnerId() + " not found", HttpStatus.NOT_FOUND);
		}
		int pId = productService.getNextProductId();
		p.setProductId(pId);

		Product newProduct = productService.storeNewProduct(p);

		try {
			azureService.uploadFiles(pId, file);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Some thing wrong with files", HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<Object>(newProduct, HttpStatus.OK);
	}

	@GetMapping("/{product_id}")
	public ResponseEntity<Object> getProductDetails(@PathVariable("product_id") int productId) {
		Product p = productService.getProductDetails(productId);
		if (Objects.isNull(p)) {
			return new ResponseEntity<Object>("Product not Found", HttpStatus.NOT_FOUND);
		}

		List<String> files = productService.getFileForProduct(productId);
		p.setFileUrls(files);

		return new ResponseEntity<Object>(p, HttpStatus.OK);
	}

	@GetMapping("/all-products")
	public ResponseEntity<Object> getAllProductInfo() {
		List<Object[]> list = productService.getAllProducts();

		return new ResponseEntity<Object>(list, HttpStatus.OK);
	}

	@PostMapping("/userItem")
	public ResponseEntity<Object> getUserItems(@RequestBody ProductUtils productUtils) {
		int userId = productUtils.ownerId;

		List<Product> productList = productService.getUserProducts(userId);

		return new ResponseEntity<Object>(productList, HttpStatus.OK);
	}

}
