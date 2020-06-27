package com.kakaopay.spreadMoney.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.kakaopay.spreadMoney.domain.entity.SpreadInfo;

public interface SpreadMoneyRepository extends MongoRepository<SpreadInfo, String>{
	
	@Query("{roomId : ?0, token : ?1}")
	public SpreadInfo findByTokenAndRoomId(String roomId, String token);
}
