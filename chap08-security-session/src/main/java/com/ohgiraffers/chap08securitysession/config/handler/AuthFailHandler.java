package com.ohgiraffers.chap08securitysession.config.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;
import java.net.URLEncoder;

// 사용자 로그인 실패 시, 실패 요청 커스텀을 위한 핸들러
// 시큐리티는 기본적으로 실패하면 index 로 보내는데 이거를 커스텀해서 보내주기 위한 핸들러.

@Configuration
public class AuthFailHandler extends SimpleUrlAuthenticationFailureHandler {


    // 실패 시 어떻게 할지 이 메소드를 통해 결정(실패 시 행동을 정할 메소드)
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorMessage = null;
        if (exception instanceof BadCredentialsException) {

            // 사용자의 아이디가 DB 에 존재하지 않는 경우 or 비밀번호가 맞지 않는 경우
            errorMessage = "아이디가 존재하지 않거나 비밀번호가 일치하지 않습니다.";

        }else if(exception instanceof InternalAuthenticationServiceException) {

            // 서버에서 사용자 정보를 검증하는 과정에서 발생하는 에러 -- 대부분 잘못된 코드
            errorMessage = "서버에서 오류가 발생되었습니다 !";

        }else if(exception instanceof AuthenticationCredentialsNotFoundException) {

            // 인증 정보가 없는 상태에서 보안처리 된 리소스에 접근하는 겨우
            errorMessage = "인증 요청이 거부되었습니다.";

        }else{
            errorMessage = "알 수 없는 오류로 로그인 요청을 처리할 수 없습니다.";

        }
        errorMessage = URLEncoder.encode(errorMessage, "UTF-8"); // 주소창에 한글 들어가면 깨지니까 인코딩 해준다.

        // 요청이 실패했을 시 보낼 곳 지정 - 인코딩 후
        setDefaultFailureUrl("/auth/fail?message=" + errorMessage);


        // 수행 부분만 정의하고 나머지는 super 에게 보내서 처리
        super.onAuthenticationFailure(request, response, exception);
    }
}
