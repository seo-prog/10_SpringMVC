package com.ohgiraffers.chap01requestmapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.awt.*;

@Controller
// 일케 해두면 디스패처서블릿이 요청을 받은 후에 controller 어노테이션 들을 뒤질꺼다.
public class MethodMappingTestController {

    @RequestMapping("/menu/regist")
    // 리퀘스트처럼 모델에 응답 attribute 를 추가한다음에 그 후 그걸 어디 경로로 보낼지 지정해주면 그 경로지정해준곳에서
    // 꺼내서 사용할 수 있다.
    public String registMenu(Model model) { // model 객체는 응답을 위한 모델
        model.addAttribute("message", "신규 메뉴 등록용 핸들러 메소드 호출");

        return "mappingResult"; // 경로 지정해준거. 내 응답을 어디로 보내서 처리할지 !
        // 일케 return 을 해주면 그 후 안에 뭔가가?  templates 아래에서 이 mappingResult 라는 애를 찾게된다 !
    }

    @RequestMapping(value = "/menu/modify", method = RequestMethod.GET) // get 요청만 받겠다
    public String modifyMenu(Model model) {
        model.addAttribute("message", "Get 방식의 메뉴 수정 호출 !");
                return "mappingResult";
    }

    /*
    * 요청 메소드 전용 어노테이션
    * 요청 메소드    어노테이션
    *   post        @PostMapping
    *   get         @GetMapping
    *   Put         @PutMapping // 수정 리소스의 모든 것을 업데이트
    * // 만약 a,b,c, 가 있는 요청에 a,b 만 보내면 자동으로 a,b 만 보관하게 된다.
    *   Delete      @DeleteMapping
    *   Patch       @PatchMapping // 수정 리소스 일부 업데이트
    * */

    @GetMapping("/menu/delete")
    public String getDeleteMenu(Model model) {
        model.addAttribute("message", "get 방식의 삭제 핸들러 메소드 호출 !");
        return "mappingResult";
    }

    @PostMapping("/menu/delete")
    public String postDeleteMenu(Model model) {
        model.addAttribute("message", "post 방식의 삭제 핸들러 메소드 호출 !");
        return "mappingResult";
    }



}
