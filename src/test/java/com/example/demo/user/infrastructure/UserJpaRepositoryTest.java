package com.example.demo.user.infrastructure;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.demo.user.domain.UserStatus;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;


@DataJpaTest(showSql = true)
@Sql("/sql/user-repository-test-data.sql")
@TestPropertySource("classpath:test-application.properties")
public class UserJpaRepositoryTest {

    @Autowired
    private UserJpaRepository userJpaRepository;

//    @Test
//    void UserRepository_가_제대로_연결되었다() {
//        //given
//        UserEntity userEntity = new UserEntity();
//        userEntity.setEmail("kdkd@gmail.com");
//        userEntity.setAddress("seoul");
//        userEntity.setNickname("가가");
//        userEntity.setStatus(UserStatus.ACTIVE);
//        userEntity.setCertificationCode("aaaaa-aaaaa-aaaaa");
//
//        //when
//        UserEntity result = userRepository.save(userEntity);
//
//        //then
//        assertThat(result.getId()).isNotNull();
//    }

    @Test
    void findByIdAndStatus_로_유저데이터를_찾아올_수_있다() {
        //given
        //when
        Optional<UserEntity> result = userJpaRepository.findByIdAndStatus(1L, UserStatus.ACTIVE);

        //then
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void findByIdAndStatus_는_데이터가_없으면_Optional_empty를_내려준다() {
        //given
        //when
        Optional<UserEntity> result = userJpaRepository.findByIdAndStatus(1L, UserStatus.PENDING);

        //then
        assertThat(result.isEmpty()).isTrue();
    }


    @Test
    void findByEmailAndStatus_로_유저데이터를_찾아올_수_있다() {
        //given
        //when
        Optional<UserEntity> result = userJpaRepository.findByEmailAndStatus("kdkd@gmail.com", UserStatus.ACTIVE);

        //then
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void findByEmailAndStatus_는_데이터가_없으면_Optional_empty를_내려준다() {
        //given
        //when
        Optional<UserEntity> result = userJpaRepository.findByEmailAndStatus("kdkd@gmail.com", UserStatus.PENDING);

        //then
        assertThat(result.isEmpty()).isTrue();
    }


}
