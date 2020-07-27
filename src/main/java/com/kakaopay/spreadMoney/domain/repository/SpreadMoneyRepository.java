package com.kakaopay.spreadMoney.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.kakaopay.spreadMoney.domain.entity.Spread;

public interface SpreadMoneyRepository extends MongoRepository<Spread, String>{
	
	@Query("{roomId : ?0, token : ?1}")
	public Spread findByTokenAndRoomId(String roomId, String token);
}
