package com.csuf.bidbash.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.csuf.bidbash.pojos.BidRequest;

@Repository
public interface BidRequestRepo extends JpaRepository<BidRequest, Integer>{

	@Query(value = "select request_id from auction_request_db order by request_id desc limit 1",nativeQuery = true)
	public Optional<Integer> nextBidRequestId();
	
}
