package org.scoula.security.util;

import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.scoula.config.RootConfig;
import org.scoula.security.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        RootConfig.class,
        SecurityConfig.class
})
@Log4j
class JwtProcessorTest {

    @Autowired
    JwtProcessor jwtProcessor;

    @Test
    void generateToken() {
        // JWT 토큰 생성 테스트
        String username = "user0";
        String token = jwtProcessor.generateToken(username); // JWT 토큰 생성
        log.info(token);
        assertNotNull(token); // 토큰이 null이 아닌지 확인하여 테스트 검증
    }

    @Test
    void getUsername() {
        // JWT 토큰에서 사용자 이름 추출하는 테스트
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMCIsImlhdCI6MTcyNDIyNDI3MywiZXhwIjoxNzI0MjI0NTczfQ.F7xbbcPUKlMFS2oK9evYc_3KVF2Is0VfMZxZVeFC9Orh_V-rfdRvvS9hJXcPwQ29n2DKi1OZyvBC8Nj0v7A4Pg";

        String username = jwtProcessor.getUsername(token); // JWT 토큰에서 사용자 이름 추출
        log.info(username);
        assertNotNull(username); // 사용자 이름이 null이 아닌지 확인
    }

    @Test
    void validateToken() {
        // 토큰 유효성 검사 테스트
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMCIsImlhdCI6MTcyNDIyNDg2OCwiZXhwIjoxNzI0MjI1MTY4fQ.emFR2PWLmy78R9uWmcGossdBfR7OLEJoa_PQAKzI3MFEuorSuqGFZYTrO-WWaTxQwIiG9_VecoUNfcgGtkq55A";
        
        boolean isValid = jwtProcessor.validateToken(token); // 토큰 유효성 검사
        log.info(isValid);
        assertTrue(isValid); // 유효성 여부 확인
    }
}