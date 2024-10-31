package com.ohgiraffers.chap08securitysession.auth.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/auth/*")
public class AuthController {

    @GetMapping("login")
    public ModelAndView login(ModelAndView mv) {
        mv.setViewName("auth/login");
        return mv;
    }

    @GetMapping("fail")
    // 로그인 실패했을 때 여기로 보낼꺼다.
    // 그 후 메세지 담아서 날릴꺼임.
    public ModelAndView loginFail(@RequestParam String message, ModelAndView mv) {

        mv.addObject("message", message);
        mv.setViewName("auth/fail");
        return mv;
    }


}
