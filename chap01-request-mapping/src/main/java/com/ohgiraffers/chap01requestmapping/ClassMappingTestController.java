package com.ohgiraffers.chap01requestmapping;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller // controller 만 리턴값이 스트링을 때 html 붙여서 매핑이 가능한거임 !다른 빈은 매핑 안됨 ! 그냥 문자열이 리턴됨 !
@RequestMapping("/order/*")
public class ClassMappingTestController {



    @GetMapping("/regist")
    public String registOrder(Model model) {
        model.addAttribute("message", "get 방식의 주문 등록용 핸들러 메소드 호출");
        return "mappingResult";
    }


    // getmapping 과 postmapping 단일 매핑밖에 안되지만 requestmapping 은 여러 요청을 받을 수 있다.
    // 단, 둘의 형식이 같은 형식일 때 !
    @RequestMapping(value = {"/modify", "delete"}, method = RequestMethod.POST)
    public String modifyOrder(Model model) {
        model.addAttribute("message", "post 방식의 주문 정보 수정 핸들러 메소드 호출");
        return "mappingResult";
    }

    // /order/1  경로에 존재하는 단순한 변수
    // /order?asd=asd 키값

    /*
    * PathVariable
    * @PathVariable 어노테이션을 이용해 변수를 받아올 수 있다..
    * path variable 로 전달되는 {변수명} 은 반드시 매개변수명과 동일해야 한다.
    * 만약 동일하지 않으면 @PathVariable("이름") 을 설정 해 주어야 한다.
    *
    * @PathVariable int orderNo 이렇게 매개변수명과 동일하면 괄오 저 이름설정은 따로 안적어줘도 되지만 안전하게 다 적어라.
    * orderNo1 얘는 사용할 변수명이 되겠지?
    * */

    @GetMapping("/detail/{orderNo}")
    public String selectOrderDetail(Model model, @PathVariable("orderNo") int orderNo1) {
        model.addAttribute("message", orderNo1 + "번 주문 상세 내용 조회용 핸들러 메소드 호출");
        return "mappingResult";
    }

    @RequestMapping //직접 지정하지 않은 order 하위 경로를 모두 잡는다 !(에러페이지 느낌)
    public String otherRequest(Model model) {
        model.addAttribute("message", "order 요청이긴 하지만 다른 기능이 준비되지 않음");
        return "mappingResult";
    }


}
