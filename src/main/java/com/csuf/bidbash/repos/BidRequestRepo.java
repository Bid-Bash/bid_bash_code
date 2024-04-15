package com.csuf.bidbash.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csuf.bidbash.pojos.BidRequest;

@Repository
public interface BidRequestRepo extends JpaRepository<BidRequest, Integer> {

	@Query(value = "select request_id from auction_request_db order by request_id desc limit 1", nativeQuery = true)
	public Optional<Integer> nextBidRequestId();

	@Query(value = "select * from auction_request_db re where re.product_id = ?1 order by request_id desc limit 1", nativeQuery = true)
	public BidRequest getBidRequest(int productId);

	@Query(value = "select re.product_id, pr.product_title, "
			+ "pr.product_desc, pr.is_available, "
			+ "max(re.bid_amount) from auction_request_db re "
			+ "inner join product_db pr "
			+ "on pr.product_id = re.product_id "
			+ "where re.user_id =?1 "
			+ "group by re.product_id", nativeQuery = true)
	public List<Object> getUserBids(int userId);
}
