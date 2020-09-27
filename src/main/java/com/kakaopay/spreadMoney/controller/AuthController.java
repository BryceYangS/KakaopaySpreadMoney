package com.kakaopay.spreadMoney.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthController {


    @ApiOperation(value = "토큰발행", httpMethod = "POST", notes = "토큰발행")
    @PostMapping("/rest/v1.0/spread/token")
    public ResponseEntity<Object> makeToken(@RequestHeader(name = "x-room-id") String roomId, @RequestHeader(name = "x-user-id") int userId) throws Exception{

        Map<String, Object> result = new HashMap<>();

        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime LocalDateTimeTypeNow = now.toLocalDateTime();
        Date dateTypeNow = Date.from(LocalDateTimeTypeNow.atZone(ZoneId.of("Asia/Seoul")).toInstant());
        Date dateTypeAfterTenMin = Date.from(LocalDateTimeTypeNow.plusMinutes(10).atZone(ZoneId.of("Asia/Seoul")).toInstant());

        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String jws = Jwts.builder()
                .setSubject("spreadMoney")
                .claim("roomId", roomId)
                .claim("userId", userId)
                .setId(RandomStringUtils.randomAlphanumeric(3))
                .setExpiration(dateTypeAfterTenMin)
                .signWith(key)
                .compact();

        Jws<Claims> parsedJws =  Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws);


        result.put("token", jws);
        result.put("parsedJws", parsedJws);
//        result.put("Verify_Token", vJwt);
//        result.put("Decoded_Token", jwt);


        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}

