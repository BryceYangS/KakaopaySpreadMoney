package com.kakaopay.spreadMoney.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AuthControllerTest {

    @Autowired
    private WebApplicationContext ctx;

    private static final String TEST_ID = "1234";
    private static final String ROOM_ID = "TEST";
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.ctx).build();
    }

    @DisplayName("토큰생성")
    @Test
    void makeTokenTest() throws Exception {
        MockHttpServletRequestBuilder builder = post("/rest/v1.0/spread/token")
                .header("x-room-id", ROOM_ID)
                .header("x-user-id", TEST_ID);

        MvcResult result = mockMvc.perform(builder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        HashMap<String, String> rtnJson = new Gson().fromJson(content, HashMap.class);

    }

}