package com.ohgiraffers.chap06thymleaf.controller;


import com.ohgiraffers.chap06thymleaf.model.MemberDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/lecture/*")
public class LectureController {

    @GetMapping("expression")
    // 요청에 보내준 쿼리스팅 파라미터는 따로 안넘겨도 자동으로 넘어간다.
    public ModelAndView expression(ModelAndView mv) {

        mv.addObject("member", new MemberDTO("홍길동", 20, '남', "서울시 서초구"));
        mv.addObject("hello", "hello <h3>Thymeleaf</h3>");
        mv.setViewName("/lecture/expression");
        return mv;

    }

    @GetMapping("conditional")
    public ModelAndView conditional(ModelAndView mv) {
        mv.addObject("num", 1);
        mv.addObject("str", "바나나");

        List<MemberDTO> membersList = new ArrayList<>();
        membersList.add(new MemberDTO("홍길동", 20, '남',  "서울시 서초구"));
        membersList.add(new MemberDTO("유관순", 18, '여',  "서울시 성북구"));
        membersList.add(new MemberDTO("장보고", 40, '남',  "서울시 노원구"));
        membersList.add(new MemberDTO("최서연", 10, '여',  "서울시 종로구"));

        mv.addObject("membersList", membersList);
        mv.setViewName("/lecture/conditional");
        return mv;
    }

    @GetMapping("fragment")
    public ModelAndView fragment(ModelAndView mv) {
        mv.addObject("test1", "value1");
        mv.addObject("test2", "value2");
        mv.setViewName("/lecture/fragment");
        return mv;
    }
}
