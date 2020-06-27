package com.kakaopay.spreadMoney.service.serviceImpl;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakaopay.spreadMoney.domain.entity.SpreadDetailInfo;
import com.kakaopay.spreadMoney.domain.entity.SpreadInfo;
import com.kakaopay.spreadMoney.domain.repository.SpreadMoneyRepository;
import com.kakaopay.spreadMoney.exception.BusinessException;
import com.kakaopay.spreadMoney.service.SpreadMoneyService;
import com.kakaopay.spreadMoney.util.DateUtil;
import com.kakaopay.spreadMoney.util.MoneyUtil;

@Service("spreadMoneyService")
public class SpreadMoneyServiceImpl implements SpreadMoneyService {

	private final Logger LOGGER = LoggerFactory.getLogger(SpreadMoneyServiceImpl.class);
	
	@Autowired
	private SpreadMoneyRepository spreadRepostory;
	
	@Override
	public String insertSpreadInfo(SpreadInfo spread) throws Exception {
		
		String token = "";
		do {
			token = RandomStringUtils.randomAlphanumeric(3);
			spread.setToken(token);
		}while(existToken(spread));
		
		List<SpreadDetailInfo> spreadDetailInfo = MoneyUtil.makeSpreadDeatilInfo(spread.getTotalMoney(),
				spread.getGetterNum());
		spread.setSpreadDetailInfo(spreadDetailInfo);
		spread.setSpreadStTime(DateUtil.getNow());
		
		spreadRepostory.save(spread);
		return token;
	}

	@Override
	public int getMoney(String token, int userId, String roomId) throws Exception {
		SpreadInfo spread = SpreadInfo.builder()
								.token(token)
								.roomId(roomId)
								.build();
		
		SpreadInfo spreadInfo = spreadRepostory.findByTokenAndRoomId(spread.getRoomId(), spread.getToken());
		
		if(spreadInfo == null)
			throw new BusinessException("다른 방에 속한 사용자는 받을 수 없습니다.");
		
		
		List<SpreadDetailInfo> spreadDetailList = spreadInfo.getSpreadDetailInfo();
		int rtnMoney = 0;
		
		if(userId == spreadInfo.getSpreadId()) 
			throw new BusinessException("자신이 뿌리기한 건은 자신이 받을 수 없습니다.");
		
		if(isGetMoney(userId, spreadDetailList))
			throw new BusinessException("뿌리기 당 한 사용자는 한 번만 받을 수 있습니다.");
		
		if(DateUtil.isAfterMinute(spreadInfo.getSpreadStTime(), 10))
			throw new BusinessException("10분이 지난 뿌리기입니다.");
		
		// 뿌린 돈 받기
		for (SpreadDetailInfo spreadDetailInfo : spreadDetailList) {
			if(spreadDetailInfo.getGetterId().equals("none")) {
				spreadDetailInfo.setGetterId(Integer.toString(userId));
				rtnMoney = spreadDetailInfo.getSpreadMoney();
				break;
			}
		}
		spreadRepostory.save(spreadInfo);
		
		return rtnMoney;
	}
	
	
	@Override
	public SpreadInfo retrieveSpreadInfo(String token, int userId, String roomId) throws Exception {
		SpreadInfo spread = SpreadInfo.builder()
				.token(token)
				.roomId(roomId)
				.build();
		
		SpreadInfo spreadInfo = spreadRepostory.findByTokenAndRoomId(spread.getRoomId(), spread.getToken());
		
		if(spreadInfo == null)
			throw new BusinessException("유효하지 않은 방 ID 또는 토큰입니다.");
		
		if(spreadInfo.getSpreadId() != userId)
			throw new BusinessException("뿌린 사람만 조회가 가능합니다.");
		
		if(DateUtil.isAfterDay(spreadInfo.getSpreadStTime(), 7))
			throw new BusinessException("7일이 지난 뿌리기입니다.");
		
		return spreadInfo;
	}
	
	@Override
	public boolean existToken(SpreadInfo spread) throws Exception {
		if (spreadRepostory.findByTokenAndRoomId(spread.getRoomId(), spread.getToken()) != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isGetMoney(int userId, List<SpreadDetailInfo> spreadDetailList) throws Exception {
		for (SpreadDetailInfo spreadDetailInfo : spreadDetailList) {
			if(spreadDetailInfo.getGetterId().equals(Integer.toString(userId))) {
				return true;
			}
		}
		return false; 
	}


}
