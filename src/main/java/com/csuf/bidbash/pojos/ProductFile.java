package com.csuf.bidbash.pojos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name="product_files")
@Entity
public class ProductFile {

	@Id
	@Column(name = "product_file_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ProductFileId;
	
	@Column(name="product_id")
	private int productId;
	
	@Column(name="string_url")
	private String url;
	
	public ProductFile() {
		super();
	}

	public ProductFile(int productId, String url) {
		super();
		this.productId = productId;
		this.url = url;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
