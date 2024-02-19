package com.csuf.bidbash.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csuf.bidbash.pojos.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
	
	@Query(value = "select product_id from product_db order by product_id desc limit 1", nativeQuery = true)
	public Optional<Integer> getNextProductId();

}
