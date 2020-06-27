package com.kakaopay.spreadMoney.service;

import java.util.List;

import com.kakaopay.spreadMoney.domain.entity.SpreadDetailInfo;
import com.kakaopay.spreadMoney.domain.entity.SpreadInfo;

public interface SpreadMoneyService {
	boolean existToken(SpreadInfo spread) throws Exception;
	String insertSpreadInfo(SpreadInfo spread) throws Exception;
	int getMoney(String token, int userId, String roomId) throws Exception;
	boolean isGetMoney(int userId, List<SpreadDetailInfo> spreadDetailList) throws Exception;
	SpreadInfo retrieveSpreadInfo(String token, int userId, String roomId) throws Exception;
}
