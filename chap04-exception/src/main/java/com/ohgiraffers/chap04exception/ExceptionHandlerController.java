package com.ohgiraffers.chap04exception;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExceptionHandlerController {

    @GetMapping("controller-null")
    public String controllerNull() {
        String str = null;
        System.out.println(str.charAt(0));
        return "main";
    }

    // Exception 처리의 우선권을 가진다.. // 클래스 안에 종속됨
    @ExceptionHandler(NullPointerException.class)
    public String exceptionHandler(NullPointerException e) {
        System.out.println("controller 레벨의 exception 처리");
        return "error/nullPointer";
    }

    @GetMapping("controller-user") // 에러를 던짐
    public String controllerUser() throws MemberRegistException {
        boolean check = true; // 테스트용으로 만든겨.
        if (check) {
            throw new MemberRegistException("입사가 불가능 합니다 !");
        }
        return "/";
    }
// 이 안 에러는 일로 오고 그 밖에 에러는 global 로 간다.
    @ExceptionHandler(MemberRegistException.class)// 에러를 처리
    public String exceptionHandler(MemberRegistException e, Model model) {
        System.out.println("controller 레벨의 exception 처리");
        model.addAttribute("exception", e);
        // 에러도 model 에 메세지를 담아서 보낼수도 있다.
        return "error/memberRegist";
    }

}
