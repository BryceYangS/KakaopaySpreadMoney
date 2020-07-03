package com.kakaopay.spreadMoney.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.spreadMoney.domain.entity.SpreadInfo;
import com.kakaopay.spreadMoney.service.SpreadMoneyService;

import io.swagger.annotations.ApiOperation;

@RestController
public class SpreadMoneyController {
	
	
	@Resource(name = "spreadMoneyService")
	private SpreadMoneyService spreadMoneyService;
	
	@ApiOperation(value = "뿌리기", httpMethod = "POST", notes = "뿌릴 금액 분배/저장")
    @PostMapping("/rest/v1.0/spread/{money}/{getterNum}")
    public String spreadModey(@RequestHeader(name = "x-room-id") String roomId, @RequestHeader(name = "x-user-id") int userId, 
    						  @PathVariable(name = "money") int money,          @PathVariable(name = "getterNum") int getterNum) throws Exception{
		SpreadInfo spread = new SpreadInfo().builder()
							.roomId(roomId)
							.spreadId(userId)
							.totalMoney(money)
							.getterNum(getterNum)
							.build();
        return spreadMoneyService.insertSpreadInfo(spread);
    }
	
	
	@ApiOperation(value = "받기", httpMethod = "PUT", notes = "뿌린 금액 받기")
    @PutMapping("/rest/v1.0/spread/{token}")
    public int getMoney(@RequestHeader(name = "x-room-id") String roomId, @RequestHeader(name = "x-user-id") int userId, @PathVariable(name = "token") String token) throws Exception{
        return spreadMoneyService.getMoney(token,userId,roomId);
    }
	
	@ApiOperation(value = "조회", httpMethod = "GET", notes = "뿌리기 조회")
    @GetMapping("/rest/v1.0/spread/{token}")
    public SpreadInfo retrieveSpreadInfo(@RequestHeader(name = "x-room-id") String roomId, @RequestHeader(name = "x-user-id") int userId, @PathVariable(name = "token") String token) throws Exception{
        return spreadMoneyService.retrieveSpreadInfo(token,userId,roomId);
    }
}
