package org.scoula.exception;

import org.scoula.member.exception.PasswordMissmatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ApiExceptionAdvice {
    @ExceptionHandler(PasswordMissmatchException.class)
    protected ResponseEntity<?> handlePasswordError(Exception e) {
        return ResponseEntity
                .status(400)
                .header(HttpHeaders.CONTENT_TYPE, "text/plain;charset=UTF-8")
                .body(e.getMessage());
    }

//    // 404 에러 처리
//    @ExceptionHandler(NoSuchElementException.class)
//    protected ResponseEntity<String> handleIllegalArgumentException(NoSuchElementException e) {
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)  // HTTP 상태 코드 404 설정
//                .header("Content-Type", "text/plain;charset=UTF-8")  // 응답 헤더 설정
//                .body("해당 ID의 요소가 없습니다.");  // 응답 바디에 메시지 설정
//    }

    // 500 에러 처리
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)  // HTTP 상태 코드 500 설정
                .header("Content-Type", "text/plain;charset=UTF-8")  // 응답 헤더 설정
                .body(e.getMessage());  // 예외 메시지를 응답 바디에 설정
    }
}
