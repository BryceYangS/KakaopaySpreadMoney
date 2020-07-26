package com.kakaopay.spreadMoney.domain.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Document(collection="spread")
public class SpreadInfo {
	@Id
	private String _id;
	
	@Indexed
	private String token;
	@Indexed
	private String roomId;
	
	private int spreadId;
//	private String spreadStTime;
	private LocalDateTime spreadStTime;
	private int totalMoney;
	private int getterNum;
	private List<SpreadDetailInfo> spreadDetailInfo;
	
	@Builder
	public SpreadInfo(String token, int spreadId, String roomId, LocalDateTime spreadStTime, int totalMoney, int getterNum, List<SpreadDetailInfo> spreadDetailInfo) {
		this.token = token;
		this.spreadId = spreadId;
		this.roomId = roomId;
		this.spreadStTime = spreadStTime;
		this.totalMoney = totalMoney;
		this.getterNum = getterNum;
		this.spreadDetailInfo = spreadDetailInfo;
	}
}
