package com.csuf.bidbash.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csuf.bidbash.pojos.AuctionSale;

@Repository
public interface AuctionSaleRepo extends JpaRepository<AuctionSale, Integer> {

	@Query(value = "select sale_id from auction_sale_db order by sale_id desc limit 1",nativeQuery = true)
	public Optional<Integer> nextAuctionSaleId();
	
	@Query(value="select product_title, bid_amount, product.product_id, sale.transaction_id, sale.auction_request_id from auction_sale_db sale \r\n"
			+ "inner join auction_request_db request on sale.auction_request_id = request.request_id \r\n"
			+ "inner join product_db product on request.product_id = product.product_id\r\n"
			+ "where request.user_id =?1 order by sale.sale_id desc;",nativeQuery = true)
	public List<Object> getUserSaleOrders(int userId);
	
	@Modifying
	@Query(value = "update auction_sale_db set transaction_id=?2 where auction_request_id=?1", nativeQuery = true)
	public int findByAuctionRequestId(int id, String transactionId);
	
}
