package com.example.demo.common.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * 컨트롤러 테스트를 위해서 mockMVC를 사용
 * 주로 api테스트를 진행하는 데 많이 사용 됨
 */
@SpringBootTest
@AutoConfigureMockMvc //MockMvc를 위한 자동 설정
@AutoConfigureTestDatabase //MockMvc를 위한 자동 설정
public class HealthCheckTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 헬스_체크_응답이_200으로_내려온다() throws Exception {
        mockMvc.perform(get("/health_check.html"))
                .andExpect(status().isOk());
    }
}
