package com.kakaopay.spreadMoney.service;

import java.util.List;

import com.kakaopay.spreadMoney.domain.entity.Spread;
import com.kakaopay.spreadMoney.domain.entity.SpreadDetail;

public interface SpreadMoneyService {
	boolean existToken(Spread spread) throws Exception;
	String insertSpread(Spread spread) throws Exception;
	int receiveMoney(String token, int userId, String roomId) throws Exception;
	boolean isReceiveMoney(int userId, List<SpreadDetail> spreadDetailList) throws Exception;
	Spread retrieveSpread(String token, int userId, String roomId) throws Exception;
}
