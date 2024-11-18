package org.scoula.exception;

import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice // 해당 클래스에서 예외를 처리하겠다
@Log4j
public class CommonExceptionAdvice {
    //    NoHandlerFoundException 예외를 처리해주겠다
    // 404 에러(NoHandlerFoundException) 발생 시 처리할 메소드
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle404(NoHandlerFoundException ex) {
        // 404 에러 발생 시, "/resources/index.html" 페이지로 리다이렉트
        return "/resources/index.html";
    }
}
