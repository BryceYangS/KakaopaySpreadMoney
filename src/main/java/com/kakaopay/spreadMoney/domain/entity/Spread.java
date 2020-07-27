package com.kakaopay.spreadMoney.domain.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Document(collection="spread")
public class Spread {
	@Id
	private String _id;
	
	@Indexed
	private String token;
	@Indexed
	private String roomId;
	
	private int spreaderId;
	private LocalDateTime generationTimestamp;
	private int totalMoney;
	private int receiverTotalNumber;
	private List<SpreadDetail> spreadDetailList;
	
	@Builder
	public Spread(String token, int spreaderId, String roomId, LocalDateTime generationTimestamp, int totalMoney, int receiverTotalNumber, List<SpreadDetail> spreadDetailList) {
		this.token = token;
		this.spreaderId = spreaderId;
		this.roomId = roomId;
		this.generationTimestamp = generationTimestamp;
		this.totalMoney = totalMoney;
		this.receiverTotalNumber = receiverTotalNumber;
		this.spreadDetailList = spreadDetailList;
	}
}
