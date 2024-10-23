package com.ohgiraffers.chap02handlermethod;


import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

@Controller
@RequestMapping("/first/*")
@SessionAttributes("id")
// id 속성은 이 컨트롤러 안에서만 저장. 진짜 session 에 저장이 아니라 모델에 감긴 값을 이 컨트롤러 안에서는 유지한다고 생각!
public class FirstController {


    // 1. WebRequest 로 요청 파라미터 전달받기 !

    @GetMapping("regist") // 반환타입이 void 면 templates 하위에 first/regist 를 찾아서 이동한다는 의미.
    public void regist() {
    } // void 면 직접 string 을 적어줄필요가 없이 바로 넘겨만 줄 수 있다.
    // 원래 void 가 아니면 요청 메세지도 적고 return 도 해줘야했음.

    @PostMapping("regist") //  파라미터를 전달받는 방법1- 파라미터를 WebRequest 로 전달받는다.(스프링에서 알아서 넣어줌)
    public String regist(Model model, WebRequest request) {
        // 파라미터로 받았으니까 파라미터로 꺼내서 담아서 보내주는거다 !
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        int categoryCode = Integer.parseInt(request.getParameter("categoryCode"));
        String message = name + "을(를) 신규 메뉴 목록의" + categoryCode +
                "번 카테고리에" + price + "원으로 등록 하셨습니다.";
        System.out.println(message);
        model.addAttribute("message", message);
        return "/first/messagePrinter";
    }




    // 2. @RequestParam 을 이용하여 파라미터 전달 받기

    @GetMapping("modify")
    public void modify() {
    }

    // required = 파라미터의 포함 여부 , name = 이름 지정, defaultValue = 기본값
    // required = true 로 해두면 modifyName 을 제대로 넘겨주지 않으면 에러를 뱉는다는 의미(빈 문자열도 가능함 아예 요청이 안넘엉올때인가봄)
    // , 그러니 false 로 해서 없더라고 에러가 안나도록 해준다.
    // defaultValue = "0" 는 기본값을 0 으로 한다.
    // = ======= 방법 1 =======
//    @PostMapping("modify")
//    public String modify(Model model,
//                         @RequestParam(required = false, name = "modifyName") String modifyName1,
//                         @RequestParam(defaultValue = "0", name = "modifyPrice") int modifyPrice1) {
//        String message = modifyName1 + "메뉴 가격을" + modifyPrice1 + "원 으로 변경 ! ";
//        System.out.println(message);
//        model.addAttribute("message", message);
//        return "/first/messagePrinter";
//    }
    // ======= 방법2 ======
    // html 에서 넘어오는 값들은 모두 string 이다 ! 그래서 Map<String, String> 으로 받은거임
    @PostMapping("modify") // map 에는 input 에 name 이 키! value 가 value 가 된다 !
    public String modify(Model model, @RequestParam Map<String, String> parameters) {

        String modifyName = parameters.get("modifyName");
        int modifyPrice = Integer.parseInt(parameters.get("modifyPrice"));
        String message = modifyName + "메뉴 가격을" + modifyPrice + "원 으로 변경 ! ";
        System.out.println(message);
        model.addAttribute("message", message);
        return "/first/messagePrinter";

    }



// 3. @ModelAttribute 를 이용하여 파라미터 전달받기

    @GetMapping("search")
    public void search() {}

    @PostMapping("search") // @ModelAttribute 여기에 바로 담아줄꺼기에 model 객체 필요 없음
    // @ModelAttribute("menu") -> 이거는 이름 지어준거임 ! 이름을 지어줬기네 menu 라는 명핑을 쓸 수 있다.
    // 이름을 안지어줄꺼면 @ModelAttribute("menu") MenuDTO MenuDTO 일케 타입에 맞게 객체명을 써줘야한다.
    public String searchMenu(@ModelAttribute("menu") MenuDTO menu) {
        System.out.println(menu);
        return "/first/searchResult";
    }



    // 4. session 이용하기
    // 4-1. httpsession 을 매개변수로 선언하면 핸들러 메소드 호출 시 세션 객체를 호출함

    @GetMapping("login")
    public void login(){}

    @PostMapping("login") // 사용자마다 주어진 고유한 session 을 가지고와 그 안에 값을 넣어줄 수 있다.
    // session 은 공유가 되므로 다른 컨트롤러냐 view (html) 에서도 접근할 수 있다.
    public String session(HttpSession session, @RequestParam String id){
        session.setAttribute("id", id);
        // 세션에 id 담기 // 전역적으로 공유되는 session
        // @SessionAttributes("id") 이 있으면 지역 세션을 만료시길 때 얘도 만료가 되는 오류가 난다. 그러니 한 컨트롤러에서는 하나의 세션만 하도록 한다.
        return "/first/loginResult";
    }

    @GetMapping("logout1") // HttpSession 얘는 브라우저당 하나의 세션이고
    public String logout(HttpSession session){
        session.invalidate();
        // 얘는 지역, 전역 모두 만료시킨다.
        // login 정보(id)를 담아둔 session 을 만료시키기
        return "first/login";
    }

    /*
    * 4-2. SessionAttribute 를 이용하여 session 에 값 담기
    * 클래스 레벨에 @SessionAttribute 어노테이션을 이용하여 세션에 값을 담을 key 를
    * 설정해두면, model 영역에 해당 key 로 값이 추가되는 경우 (지역)Session 에 자동 등록한다..
    * ( @SessionAttribute 로 지정된 속성은 해당 컨트롤러 내에서만 유효하다.)
    * */

    @PostMapping("login2") // Model 에 귀속되는 세션
    public String sessionTest2(Model model, @RequestParam String id){
        model.addAttribute("id", id);
        // 위에 어노테이션에서 id 를 지정했으므로 id 는 이 컨트롤러에서만 지속되는 session 이다.
        // 특정 컨트롤러에 귀속되는 세션이다.
        // model 객체에 위 어노테이션의 지정 객체인 id 를 넣어주면 지역 session 이 되는거다.
        return "first/loginResult";
    }

    // sessionAttribute 로 등록된 값은 session 의 상태를 관리하는
    // sessionStatus 의 setComplete() 메소드를 호출해야 사용이 만료된다..

    @GetMapping("logout2")
    public String logout2(SessionStatus status){
        // setComplete는 세션에 존재하는 @SessionAttributes("id") 이것을 만료시키는 것이다.
        status.setComplete();
        return "first/loginResult";
    }


    // 5.@RequestBody 를 이용하여 파라미터 전달 받기

    @GetMapping("body")
    public void body(){} // 이려면 templates 에서 body 를 찾는거다. 알?

    /*
    * 5. RequestBody 를 이용하는 방법
    * 해당 어노테이션은 http 본문 자체를 읽는 부분을 모델로 변환시켜 주는 어노테이션이다.
    **/


    @PostMapping("body")
    // @RequestBody 는 리액트에서 patch 로 요청을 받을 때 body 를 받게되는데 json 으로 객체를 받을 때 많이 쓰인다.
    public void bodyTest(@RequestBody String body) throws UnsupportedEncodingException {
        System.out.println(body); // 우리가 날린 요청이 key, value 형태로 바디에 담겨져 온다.
        System.out.println(URLDecoder.decode(body, "UTF-8"));
    }
}
