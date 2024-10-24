package com.ohgiraffers.chap04exception;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // Exception 이 발생 했을 때 핸들링 해 주는 클래스를 만드는 어노테이션
public class GlobalExceptionHandler {
    // 각 컨트롤러 별 핸들러가 우선순위 이고 해당 컨트롤러에 예외 처리 메소드가 없다면 이리로 오는것이다.!

    @ExceptionHandler(NullPointerException.class)
    public String handleNullPointerException(NullPointerException e) {
        System.out.println("Global (서버에서 발생하는 모든) 레벨의 Exception 처리");
        return "error/nullPointer";
    }

    // MemberRegistException.class 이거를 우리가 발생시킨 에러로 봐야한다.
    @ExceptionHandler(MemberRegistException.class)
    public String handleMemberRegistException(MemberRegistException e, Model model) {
        System.out.println("Global 레벨의 exception 처리");
        model.addAttribute("exception", e);
        return "error/memberRegist";
    }

    @ExceptionHandler(Exception.class)
    // 몇가지 예외 처리는 지정을 해놓고 그 나머지의 에러는 한번에 처리할 수 있다.
    public String handleException(Exception e) {
        System.out.println("나머지 ." +
                "exception 발생함");
        return "error/default";
    }
}
