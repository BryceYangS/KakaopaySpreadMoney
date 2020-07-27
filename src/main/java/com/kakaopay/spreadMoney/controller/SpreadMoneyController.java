package com.kakaopay.spreadMoney.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.spreadMoney.domain.entity.Spread;
import com.kakaopay.spreadMoney.service.SpreadMoneyService;

import io.swagger.annotations.ApiOperation;

@RestController
public class SpreadMoneyController {
	
	
	@Resource(name = "spreadMoneyService")
	private SpreadMoneyService spreadMoneyService;
	
	@ApiOperation(value = "뿌리기", httpMethod = "POST", notes = "뿌릴 금액 분배/저장")
	@PostMapping("/rest/v1.0/spread/{money}/{getterNum}")
	public ResponseEntity<Object> spreadModey(@RequestHeader(name = "x-room-id") String roomId, @RequestHeader(name = "x-user-id") int userId, 
			@PathVariable(name = "money") int money,          @PathVariable(name = "getterNum") int getterNum) throws Exception{
		Spread spread = new Spread().builder()
				.roomId(roomId)
				.spreaderId(userId)
				.totalMoney(money)
				.receiverTotalNumber(getterNum)
				.build();
		Map<String, String> result = new HashMap<String, String>();
		result.put("token", spreadMoneyService.insertSpread(spread));
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "받기", httpMethod = "PUT", notes = "뿌린 금액 받기")
    @PutMapping("/rest/v1.0/spread/{token}")
    public ResponseEntity<Object> receiveMoney(@RequestHeader(name = "x-room-id") String roomId, @RequestHeader(name = "x-user-id") int userId, @PathVariable(name = "token") String token) throws Exception{
		Map<String, Integer> result = new HashMap<String, Integer>();
		result.put("money", spreadMoneyService.receiveMoney(token,userId,roomId));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
	
	@ApiOperation(value = "조회", httpMethod = "GET", notes = "뿌리기 조회")
    @GetMapping("/rest/v1.0/spread/{token}")
    public ResponseEntity<Object> retrieveSpreadInfo(@RequestHeader(name = "x-room-id") String roomId, @RequestHeader(name = "x-user-id") int userId, @PathVariable(name = "token") String token) throws Exception{
        return new ResponseEntity<>(spreadMoneyService.retrieveSpread(token,userId,roomId), HttpStatus.OK);
    }
}
