package com.joebrooks.mapshotservice.admin.domain.login;

import static org.assertj.core.api.Assertions.assertThat;

import java.security.NoSuchAlgorithmException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AdminServiceTest {

    @Autowired
    private AdminService adminService;


    @Test
    void 잘못된_관리자_아이디_사용시_false_리턴() throws NoSuchAlgorithmException {
        assertThat(adminService.login(AdminLoginRequest.builder()
                .nickName("asdf")
                .password("asdf")
                .build())).isFalse();
    }

    @Test
    void 올바른_관리자_아이디_사용시_true_리턴() throws NoSuchAlgorithmException {
        assertThat(adminService.login(AdminLoginRequest.builder()
                .nickName("admin")
                .password("1234")
                .build())).isTrue();
    }
}