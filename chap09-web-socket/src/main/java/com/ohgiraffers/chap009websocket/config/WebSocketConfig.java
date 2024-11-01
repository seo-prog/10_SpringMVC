package com.ohgiraffers.chap009websocket.config;


import com.ohgiraffers.chap009websocket.server.ChatWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {


    @Override
    // 웹소켓(WebSocket) 핸들러를 등록하는 메소드다 ! // 서버측에 만들어둔 핸들러 등록 ! // 소켓은 요청때마다 관리
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //registerWebSocketHandlers 를 우리가 구현을 해주면 스프링에서 자동 실행을 해준다 !
        // 요청 왔을 때, 여기서 소켓 요청 관리

        registry.addHandler(new ChatWebSocketHandler(), "/chattingServer").setAllowedOrigins("*"); //* => 모든곳에서 접근을 작동을 하겠다는 뜻
        // 사용자 측에서 이 요청(/chattingServer) 이 들어왔을 때, 우리가 지정한 웹소켓 핸들러에서 관리해서 통신을 하도록 설정을 해주겠다.







    }
}
