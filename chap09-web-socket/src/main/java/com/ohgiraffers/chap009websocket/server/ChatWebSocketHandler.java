package com.ohgiraffers.chap009websocket.server;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class ChatWebSocketHandler extends TextWebSocketHandler {
    // 사용자 세션 관리
    // 접속한 클라이언트의 webSocketSession 을 관리할 set

    private static Set<WebSocketSession> clients = Collections.synchronizedSet(new HashSet<>());
    //synchronizedSet -> 다수의 사용자가 이용할 때 안전, 성능도 좋음. (동기화 셋?)


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        // 클라이언트로부터 텍스트 메세지를 수신했을 떄 호출되는 메소드

        // 다수의 사용자 누구라도 메세지를 보내면 일루 온다.
        System.out.println("메세지 출력 : " + session.getId() + ":" + message.getPayload());
        synchronized (clients) { // synchronized -> 안전하게 전송해준다.
            for(WebSocketSession client : clients) {
                if(!client.equals(session)) { // 자신 빼고 메세지를 다른 사람들에게 뿌려준다.(session 을 가진 다른 사람들)
                    // 메세지를 자기 자신을 제외하고 전송
                    client.sendMessage(new TextMessage(message.getPayload()));
                }}}}

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
       // 통신 중 에러가 발생했을 때 호출되는 메소드
        System.out.println("에러 발생 : " + session.getId());
        exception.printStackTrace(); // 어느 세션에서 에러가 발생한건지 체크. // 세션 이름과 익셉션 내용을 보겠다는 내용.
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 클라이언트가 webSocket 연결을 성공적으로 수행했을 때 호출되는 메소드
        clients.add(session);
        System.out.println("웹소켓 연결 : " + session.getId());
        // session에 소켓세션의 id . 실행시마다 달라질겨 // 소켓 연결 시 생성되는 id
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
       // 클라이언트가 웹소켓 연결을 닫았을 때 호출되는 메소드
        clients.remove(session);
        System.out.println("웹소켓 종료 : " + session.getId()); // 어떤 사용자가 종료했는지 체크

    }
}
