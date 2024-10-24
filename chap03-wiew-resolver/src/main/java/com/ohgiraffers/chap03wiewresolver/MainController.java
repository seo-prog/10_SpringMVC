package com.ohgiraffers.chap03wiewresolver;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    // "/" 이거는 주소만 쳤을 때 해당된다. 즉, 첫 페이지
    // index 없이 기본 요청 매핑을 해두고 작성을 할것이다.
    @RequestMapping(value = {"/","/main"})
    public String main() {
        return "main";
    }

    /*
    resource 에서 static - js 나 css 같은 정적 자원들을 미리 업로드해서 가져오는 방식으로 사용하기 위함.
    templates - html 같은 자원을 동적으로 관리하기 위해 사용

    * */
}
