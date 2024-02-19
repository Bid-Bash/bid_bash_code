package com.csuf.bidbash.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csuf.bidbash.pojos.ProductFile;

@Repository
public interface ProductFileRepo extends JpaRepository<ProductFile, Integer> {
	
	@Query(value = "select string_url from product_files where product_id = ?1", nativeQuery = true)
	public List<String> getProductFilesByProductId(int productId);

}
