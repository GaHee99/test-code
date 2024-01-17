package com.example.demo.user.controller;

import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.domain.UserUpdate;
import com.example.demo.user.infrastructure.UserEntity;
import com.example.demo.user.infrastructure.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SqlGroup({
        @Sql(value = "/sql/user-controller-test-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
})
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test // [GET]
    void 사용자는_특정_유저의_정보를_전달_받을_수_있다() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("kdkd@gmail.com"))
                .andExpect(jsonPath("$.nickname").value("가가"))
                .andExpect(jsonPath("$.address").doesNotExist())
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test // [GET] + 404 status Code
    void 사용자는_존재하지_않는_유저의_아이디로_api호출을_할_경우_404_응답을_받는다() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/api/users/123456789"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Users에서 ID 123456789를 찾을 수 없습니다."));
    }

    @Test // [GET] + add queryParam
    void 사용자는_인증코드로_계정을_활성화_할_수_있다() throws Exception {
       //given
        //when
        //then
        mockMvc.perform(get("/api/users/2/verify")
                .queryParam("certificationCode", "aaaaa-aaaaa-aaaab"))
                .andExpect(status().isFound());
        UserEntity userEntity = userRepository.findById(2L).get();
        assertThat(userEntity.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test // [GET] + response json value 추출 + header넣기
    void 사용자는_내_정보를_불러올_때_개인정보인_주소도_갖고_올_수_있다() throws Exception{
        //given
        //when
        //then
        mockMvc.perform(
                get("/api/users/me")
                .header("EMAIL", "kdkd@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("kdkd@gmail.com"))
                .andExpect(jsonPath("$.nickname").value("가가"))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.address").value("seoul"));
    }

    @Test // [PUT] + ObjectMapper + json request추가
    void 사용자는_내_정보를_수정할_수_있다() throws Exception{
        //given
        UserUpdate userUpdate = UserUpdate.builder()
                        .nickname("gahee")
                        .address("gonduck")
                                .build();
        //when
        //then
        mockMvc.perform(
                        put("/api/users/me")
                                .header("EMAIL", "kdkd@gmail.com")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("kdkd@gmail.com"))
                .andExpect(jsonPath("$.nickname").value("gahee"))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.address").value("gonduck"));
    }


}
