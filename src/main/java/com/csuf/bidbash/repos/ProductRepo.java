package com.csuf.bidbash.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csuf.bidbash.pojos.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
	
	@Query(value = "select product_id from product_db order by product_id desc limit 1", nativeQuery = true)
	public Optional<Integer> getNextProductId();
	
	@Modifying
	@Query(value = "update product_db set current_bid =?2 where product_id= ?1", nativeQuery = true)
	public int updateBidAmount(int pId, double bidAmount);
	
	@Query(value = "select p.*, max(f.string_url) from product_db p inner join product_files f on p.product_id = f.product_id where p.is_available = 1 group by p.product_id order by p.product_id desc", nativeQuery = true)
	public List<Object[]> getAllProductAvailable();
	
	@Query(value = "select * from product_db pr where pr.owner_id = ?1 order by pr.product_id desc", nativeQuery = true)
	public List<Product> getAllProductsByOwnerId(int ownerId);

}
