package com.ohgiraffers.chap009websocket.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChatController {

    @GetMapping("/chat")
    // 인덱스 html 에서 아이디를 경로에 담아서 넘어오는 부분.

    public ModelAndView chatPage(@RequestParam("userId") String userId, ModelAndView mv) {

        System.out.println("userId = " + userId);

        mv.addObject("userId", userId);
        mv.setViewName("chatWindow"); // id 담아서 chatWindow 로 날린다.
        return mv;
    }
}
