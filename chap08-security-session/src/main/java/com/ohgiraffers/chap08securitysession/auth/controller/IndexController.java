package com.ohgiraffers.chap08securitysession.auth.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @GetMapping
    public String root() {
        return "index";
    }

    // index 에서 admin 으로 요청 날리면 요기

    @GetMapping("/admin/page")
    public ModelAndView admin(ModelAndView mv) {
        mv.setViewName("admin/admin");
        return mv;
    }

    // index 에서 user 로 요청 날리면 요기
    @GetMapping("user/page")
    public ModelAndView user(ModelAndView mv) {
        mv.setViewName("user/user");
        return mv;
    }

}
