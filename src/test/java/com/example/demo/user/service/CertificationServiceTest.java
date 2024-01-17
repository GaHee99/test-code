package com.example.demo.user.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.demo.mock.FakeMailSender;
import org.junit.jupiter.api.Test;

public class CertificationServiceTest {

    @Test
    public void 이메일과_컨텐츠가_제대로_만들어져서_보내지는지_테스트한다() {
        //given
        FakeMailSender fakeMailSender = new FakeMailSender();
        CertificationService certificationService = new CertificationService(fakeMailSender);

        //when
        certificationService.send("abcd@naver.com", 1,"aaaaa-aaaaa-aaaaa");

        //then
        assertThat(fakeMailSender.email).isEqualTo("abcd@naver.com");
        assertThat(fakeMailSender.title).isEqualTo("Please certify your email address");
    }
}
