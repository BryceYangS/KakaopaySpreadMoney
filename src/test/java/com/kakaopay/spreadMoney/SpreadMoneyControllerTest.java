package com.kakaopay.spreadMoney;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class SpreadMoneyControllerTest {
	@Autowired
	private WebApplicationContext ctx;
	
	
	private MockMvc mockMvc;;
	
	private static final String TEST_ID = "1234";
	private static final String ROOM_ID = "TEST";
	private static String test_token = null;
	
	@BeforeEach
	public void initTest() {
		if(mockMvc == null) {
			mockMvc = MockMvcBuilders.webAppContextSetup(this.ctx).build();
		}
	}
	
	
	@DisplayName("뿌리기 ")
	@Test
	@Order(1)
	void testInsertSpreadInfo() throws Exception {
		MockHttpServletRequestBuilder builder = post("/rest/v1.0/spread/2000/10")
													.header("x-room-id", ROOM_ID)
													.header("x-user-id", TEST_ID);
		
		MvcResult result = mockMvc.perform(builder)
								.andDo(print())
								.andExpect(status().isOk())
								.andReturn();
		String content = result.getResponse().getContentAsString();
		test_token = content;
	}
	
	@DisplayName("받기 : 뿌린사람인 경우")
	@Test
	@Order(2)
	void testgetMoney1() throws Exception {
		MockHttpServletRequestBuilder builder = put("/rest/v1.0/spread/" + test_token)
													.header("x-room-id", ROOM_ID)
													.header("x-user-id", TEST_ID);
		
		MvcResult result = mockMvc.perform(builder)
								.andDo(print())
								.andExpect(status().isExpectationFailed())
								.andReturn();
	}
	
	
	@DisplayName("받기 : 뿌린 사람 아닌 경우")
	@Test
	@Order(3)
	void testgetMoney2() throws Exception {
		MockHttpServletRequestBuilder builder = put("/rest/v1.0/spread/" + test_token)
													.header("x-room-id", ROOM_ID)
													.header("x-user-id", "3456");
		
		
		MvcResult result = mockMvc.perform(builder)
								.andDo(print())
								.andExpect(status().isOk())
								.andReturn();
	}
	
	@DisplayName("받기 : 뿌린 사람 아닌 경우 & 이미 받은 경우")
	@Test
	@Order(3)
	void testgetMoney3() throws Exception {
		MockHttpServletRequestBuilder builder = put("/rest/v1.0/spread/" + test_token)
													.header("x-room-id", ROOM_ID)
													.header("x-user-id", "3456");
		
		
		MvcResult result = mockMvc.perform(builder)
								.andDo(print())
								.andExpect(status().isExpectationFailed())
								.andReturn();
	}
	
	@DisplayName("조회")
	@Test
	@Order(4)
	void testRetrieveSpreadInfo() throws Exception {
		MockHttpServletRequestBuilder builder = get("/rest/v1.0/spread/" + test_token)
													.header("x-room-id", ROOM_ID)
													.header("x-user-id", TEST_ID);
		
		MvcResult result = mockMvc.perform(builder)
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}
}
