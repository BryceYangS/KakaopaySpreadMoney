package com.kakaopay.spreadMoney.util;

import java.util.ArrayList;
import java.util.List;

import com.kakaopay.spreadMoney.domain.entity.SpreadDetailInfo;

public class MoneyUtil {

	public static List<SpreadDetailInfo> makeSpreadDeatilInfo(int totalMoney, int getterNum){
		List<SpreadDetailInfo> spreadDtlInfo = new ArrayList<>();
		int[] spreadMoney = new int[getterNum];
		
		if(isDivideMoney(totalMoney, getterNum)) {
			for (int i = 0; i < spreadMoney.length; i++) {
				spreadMoney[i] = totalMoney / getterNum;
			}
		}else {
			for (int i = 0; i < spreadMoney.length; i++) {
				int tmpSpreadMoney = totalMoney / getterNum;
				if(i == 0) tmpSpreadMoney += totalMoney % getterNum;
				spreadMoney[i] = tmpSpreadMoney;
			}
		}
		
		for(int i=0; i < getterNum; i++) {
			SpreadDetailInfo dtlInfo = new SpreadDetailInfo();
			dtlInfo.setNo(i);
			dtlInfo.setGetterId("none");
			dtlInfo.setSpreadMoney(spreadMoney[i]);
			spreadDtlInfo.add(dtlInfo);
		}
		return spreadDtlInfo;
	}
	
	public static boolean isDivideMoney(int totalMoney, int getterNum) {
		boolean isDividMoney = (totalMoney % getterNum) == 0 ? true : false;  
		return isDividMoney;
	}
}
