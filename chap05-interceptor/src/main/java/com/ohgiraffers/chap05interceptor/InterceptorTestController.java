package com.ohgiraffers.chap05interceptor;


import org.aopalliance.intercept.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/*")
public class InterceptorTestController {

    @Autowired
    private InterceptorService interceptorService;
    // 컨트롤러가 사용될 때 주입되어있겠찌?

    @PostMapping("stopwatch")
    public String handlerMethod(Model model) throws InterruptedException {
        model.addAttribute("test", "모델 테스트");
        System.out.println("핸들러 메소드 호출함..");
        interceptorService.method();
        Thread.sleep(1000);// 프로그램 1초 대기
        // Thread 는 한번에 프로그램이 실행할 수 있는 실행 단위인데
        // 프로그램 실행되다가 1초 멈춰라 라는 의미. 일반적으로 싱글스레드
        return "result";
    }
}
