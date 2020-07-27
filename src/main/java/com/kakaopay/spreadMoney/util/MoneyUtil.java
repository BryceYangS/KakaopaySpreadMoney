package com.kakaopay.spreadMoney.util;

import java.util.ArrayList;
import java.util.List;

import com.kakaopay.spreadMoney.domain.entity.SpreadDetail;

public class MoneyUtil {

	public static List<SpreadDetail> makeSpreadDeatilList(int totalMoney, int getterNum){
		List<SpreadDetail> spreadDtlList = new ArrayList<>();
		int[] spreadMoney = new int[getterNum];
		
		for (int i = 0; i < spreadMoney.length; i++) {
			int tmpSpreadMoney = totalMoney / getterNum;
			if(i == 0) tmpSpreadMoney += totalMoney % getterNum;
			spreadMoney[i] = tmpSpreadMoney;
		}
		
		for(int i=0; i < getterNum; i++) {
			SpreadDetail dtl = new SpreadDetail();
			dtl.setNo(i);
			dtl.setReceiverId("none");
			dtl.setSpreadMoney(spreadMoney[i]);
			spreadDtlList.add(dtl);
		}
		return spreadDtlList;
	}
	
	public static boolean isDivideMoney(int totalMoney, int getterNum) {
		boolean isDividMoney = (totalMoney % getterNum) == 0 ? true : false;  
		return isDividMoney;
	}
}
