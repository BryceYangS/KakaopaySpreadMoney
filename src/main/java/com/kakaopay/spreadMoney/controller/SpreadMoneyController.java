package com.kakaopay.spreadMoney.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.spreadMoney.domain.entity.SpreadInfo;
import com.kakaopay.spreadMoney.service.SpreadMoneyService;

import io.swagger.annotations.ApiOperation;

@RestController
public class SpreadMoneyController {
	
	
	@Resource(name = "spreadMoneyService")
	private SpreadMoneyService spreadMoneyService;
	
	@Autowired
	private HttpServletRequest request;
	
	@ApiOperation(value = "뿌리기", httpMethod = "POST", notes = "뿌릴 금액 분배/저장")
    @PostMapping("/rest/v1.0/spread/{money}/{getterNum}")
    public String spreadModey(@PathVariable(name = "money") int money, @PathVariable(name = "getterNum") int getterNum) throws Exception{
		Map<String, String> headerInfo = getHeadersInfo();
		SpreadInfo spread = new SpreadInfo().builder()
							.roomId(headerInfo.get("x-room-id"))
							.spreadId(Integer.parseInt(headerInfo.get("x-user-id")))
							.totalMoney(money)
							.getterNum(getterNum)
							.build();
        return spreadMoneyService.insertSpreadInfo(spread);
    }
	
	
	@ApiOperation(value = "받기", httpMethod = "PUT", notes = "뿌린 금액 받기")
    @PutMapping("/rest/v1.0/spread/{token}")
    public int getMoney(@PathVariable(name = "token") String token) throws Exception{
		Map<String, String> headerInfo = getHeadersInfo();
        return spreadMoneyService.getMoney(token,Integer.parseInt(headerInfo.get("x-user-id")),headerInfo.get("x-room-id"));
    }
	
	@ApiOperation(value = "조회", httpMethod = "GET", notes = "뿌리기 조회")
    @GetMapping("/rest/v1.0/spread/{token}")
    public SpreadInfo retrieveSpreadInfo(@PathVariable(name = "token") String token) throws Exception{
		Map<String, String> headerInfo = getHeadersInfo();
        return spreadMoneyService.retrieveSpreadInfo(token,Integer.parseInt(headerInfo.get("x-user-id")),headerInfo.get("x-room-id"));
    }
	
	
	
	//get request headers
	private Map<String, String> getHeadersInfo() {

		Map<String, String> map = new HashMap<String, String>();

		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			map.put(key, value);
		}

		return map;
	}

	
}
