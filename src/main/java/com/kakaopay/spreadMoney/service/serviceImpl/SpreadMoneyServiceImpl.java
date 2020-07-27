package com.kakaopay.spreadMoney.service.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.kakaopay.spreadMoney.domain.entity.Spread;
import com.kakaopay.spreadMoney.domain.entity.SpreadDetail;
import com.kakaopay.spreadMoney.domain.repository.SpreadMoneyRepository;
import com.kakaopay.spreadMoney.exception.BusinessException;
import com.kakaopay.spreadMoney.service.SpreadMoneyService;
import com.kakaopay.spreadMoney.util.DateUtil;
import com.kakaopay.spreadMoney.util.MoneyUtil;
import com.mongodb.client.result.UpdateResult;

@Service("spreadMoneyService")
public class SpreadMoneyServiceImpl implements SpreadMoneyService {

	private final Logger LOGGER = LoggerFactory.getLogger(SpreadMoneyServiceImpl.class);
	
	@Autowired
	private SpreadMoneyRepository spreadRepostory;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public String insertSpread(Spread spread) throws Exception {
		
		String token = "";
		do {
			token = RandomStringUtils.randomAlphanumeric(3);
			spread.setToken(token);
		}while(existToken(spread));
		
		List<SpreadDetail> spreadDetailList = MoneyUtil.makeSpreadDeatilList(spread.getTotalMoney(), spread.getReceiverTotalNumber());
		spread.setSpreadDetailList(spreadDetailList);
		spread.setGenerationTimestamp(LocalDateTime.now());
		
		spreadRepostory.save(spread);
		return token;
	}

	@Override
	public int receiveMoney(String token, int userId, String roomId) throws Exception {
		Spread tmpSpread = Spread.builder()
								.token(token)
								.roomId(roomId)
								.build();
		
		Spread spread = spreadRepostory.findByTokenAndRoomId(tmpSpread.getRoomId(), tmpSpread.getToken());
		
		if(spread == null)
			throw new BusinessException("다른 방에 속한 사용자는 받을 수 없습니다.");
		
		
		List<SpreadDetail> spreadDetailList = spread.getSpreadDetailList();
		int rtnMoney = 0;
		
		if(userId == spread.getSpreaderId()) 
			throw new BusinessException("자신이 뿌리기한 건은 자신이 받을 수 없습니다.");
		
		if(isReceiveMoney(userId, spreadDetailList))
			throw new BusinessException("뿌리기 당 한 사용자는 한 번만 받을 수 있습니다.");
		
		if(DateUtil.isAfterMinute(spread.getGenerationTimestamp(), 10))
			throw new BusinessException("10분이 지난 뿌리기입니다.");
		
		// 뿌린 돈 받기
		Query query = new Query();
		query.addCriteria(Criteria.where("roomId").is(spread.getRoomId()).and("token").is(spread.getToken()).and("spreadDetailList.receiverId").is("none"));
		
		Update update = new Update().set("spreadDetailList.$.receiverId", Integer.toString(userId));
		
		UpdateResult result = mongoTemplate.updateFirst(query, update, Spread.class);
		
		if(result.getModifiedCount() == 0) 
			throw new BusinessException("이미 다 뿌려진 돈입니다.");
		
		
		spread = spreadRepostory.findByTokenAndRoomId(spread.getRoomId(), spread.getToken());
		
		for (SpreadDetail spreadDetailInfo : spread.getSpreadDetailList()) {
			if(spreadDetailInfo.getReceiverId().equals(Integer.toString(userId))) {
				rtnMoney = spreadDetailInfo.getSpreadMoney();
				break;
			}
		}
		
		return rtnMoney;
	}
	
	
	@Override
	public Spread retrieveSpread(String token, int userId, String roomId) throws Exception {
		Spread tmpSpread = Spread.builder()
				.token(token)
				.roomId(roomId)
				.build();
		
		Spread spread = spreadRepostory.findByTokenAndRoomId(tmpSpread.getRoomId(), tmpSpread.getToken());
		
		if(spread == null)
			throw new BusinessException("유효하지 않은 방 ID 또는 토큰입니다.");
		
		if(spread.getSpreaderId() != userId)
			throw new BusinessException("뿌린 사람만 조회가 가능합니다.");
		
		if(DateUtil.isAfterDay(spread.getGenerationTimestamp(), 7))
			throw new BusinessException("7일이 지난 뿌리기입니다.");
		
		return spread;
	}
	
	@Override
	public boolean existToken(Spread spread) throws Exception {
		if (spreadRepostory.findByTokenAndRoomId(spread.getRoomId(), spread.getToken()) != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isReceiveMoney(int userId, List<SpreadDetail> spreadDetailList) throws Exception {
		for (SpreadDetail spreadDetail : spreadDetailList) {
			if(spreadDetail.getReceiverId().equals(Integer.toString(userId))) {
				return true;
			}
		}
		return false; 
	}


}
