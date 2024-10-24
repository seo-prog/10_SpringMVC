package com.ohgiraffers.chap03wiewresolver;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/*")
public class ResolverController {

    @RequestMapping("string") // 뭐 새로 입력을 받는 창을 보여줘야 할때 void 로 페이지 전환만 해주는거임.
    public String resolver(Model model) {
        model.addAttribute("sendMessage", "문자열로 뷰 이름 반환");
        return "result";
    }

    @GetMapping("string-redirect")
    // 접두사로 redirect: 를 하면 redirect 시킨다.
    public String resolverRedirect() {
        return "redirect:/"; // 리렌더링 하고 있는거임 // 스프링에서는 redirect: 하면 리다이렉트 가능

    }

    @GetMapping("string-redirect-attr")
    public String resolverRedirectAttr(RedirectAttributes redirectAttributes) {
       /*
       * 리다이렉트 시 flash 영역에 담아서 redirect 할 수 있다..
       * flash - 1회용 세션
       * 자동으로 모델에 추가되기 때문에 requestScope 에서 값을 꺼내면 된다..
       * 세션에 임의로 값을 담고 소멸하는 방식이기 떄문에 session 에 동일한 키 없이 존재하지 않아야 한다.
       * */
        // 잠깐 세션에 담겼다가 뷰에 오면 끝난다는 의미이다.
        redirectAttributes.addFlashAttribute("flashMessage1",
                "redirect attr 사용하여 redirect");
        return "redirect:/"; // 뒤에 / 이 주소로 가게되는거다.
        // 즉, maincontroller 로 갔다가 main.html 로 가게될것이다.

    }
    /*
    * PRG 패턴 (post/ redirect/get)
    * 서버가 post 로 받은 데이터를 처리한 후 리다이렉트 응답을 클라이언트에세 보낸다..
    * 클라이언트는 리다이렉드 된 URL 로 get 요청을 보내고 그 결과를 화면에 표시한다..
    * 이렇게 되면 이후 새로고핌 시에도 get 요펑을 보내기 때문에 중복 데이터 처리가 발생하지 않는다.
    * */

    /*
    * ModelAndView - spring 에서 모델과 뷰를 함계 처리하기 위한 클래스.
    * */

    @GetMapping("modelandview")
    public ModelAndView modelandview(ModelAndView mv) {
        mv.addObject("sendMessage", "ModelAndView 를 이용한 모델과 뷰 반환");
        mv.setViewName("result");
        // 그냥 mv 에 어트리뷰트추가와 경로를 둘 다 담아서 보내주면 된다는 의미이다.
        // 평소에 return 에 view 이름을 적던걸 mv 에서는 setViewName 에 적어준다. 이게 그 view 경로로 가겠다는 의미이다.
        return mv;
    }

    @GetMapping("modelandview-redirect")
    public ModelAndView modelandviewRedirect(ModelAndView mv) {
        mv.addObject("flashMessage2", "test");
        // redirect 시에 일반 attribute 에 담으면 담아ㄱㅏ지 못하므로 FlashAttribute 를 이용해줘야한다.
        mv.setViewName("redirect:/");
        return mv;
    }

    @GetMapping("modelandview-redirect-attr")
    public ModelAndView modelandviewRedirectAttr(ModelAndView mv, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("flashMessage2",
                "ModelAndView 를 이용한 attr");
        mv.setViewName("redirect:/");
        // redirect 가 일반 어트리뷰트는 뷰에서 읽지 못한다. 요청이 끝났으므로 일반 어트리뷰트는 유지가 안된다.
        return mv;
    }
}
