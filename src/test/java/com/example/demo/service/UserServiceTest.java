package com.example.demo.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;


//(1, 'kdkd@gmail.com', '가가', 'seoul', 'aaaaa-aaaaa-aaaaa', 'ACTIVE', 0);
@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/user-service-test-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
})
@TestPropertySource("classpath:test-application.properties")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void getByEmail은_active_상태인_유저를_찾아올_수_있다() {
        //given
        String email = "kdkd@gmail.com";

        //when
        UserEntity result = userService.getByEmail(email);

        //then
        assertThat(result.getNickname()).isEqualTo("가가");
    }

    @Test
    void getByID는_active_상태인_유저를_찾아올_수_있다() {
        //given
        //when
        UserEntity result = userService.getById(1);

        //then
        assertThat(result.getNickname()).isEqualTo("가가");
    }



    @Test
    void getByEmail은_PENDING_상태인_유저를_찾아올_수_있다() {
        //given
        String email = "kdkd2@gmail.com";

        //when
        //then
        assertThatThrownBy(() -> {
            UserEntity result = userService.getByEmail(email);
        }).isInstanceOf(ResourceNotFoundException.class);
    }



}
