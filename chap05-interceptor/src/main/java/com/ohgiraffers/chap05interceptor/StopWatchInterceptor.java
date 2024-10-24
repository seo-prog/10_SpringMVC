package com.ohgiraffers.chap05interceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aopalliance.intercept.Interceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/*
* default 메소드가 존재하기 이전에는 모두 오버라이딩을 해야 하는 책임을 가지기 때문에
* 뭐시기어댑터를 이용해 사용했으나, default 메소드가 인터페이스에 사용 가능하게 된
* java 8  버전 이후에는 인터페이스만 구현하여 필요한 메소드만 오버라이딩 해서 사용할 수 있다.
*
* // HandlerInterceptor 이 인터페이스를 사용받았어도 우리가 이거는 default method 가
    // 있기에 결론적으로는 다 구현하지 않고 필요한 메소드만 오버라이딩 해서 구현할 수 있게 되었다/.
* */

@Component
public class StopWatchInterceptor implements HandlerInterceptor {
    // 이 3개중 필요한것만 골라서 사용할 수 있다.


    // 전처리 메소드 - 지정된 컨트롤러의 동작 이전에 수행할 내용 // 성능,검증 가능
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 여기 request 는 컨트롤러로 가기 이전에 낚아채 온거기 때문에 유저가 보낸 요청이 들어있다.
        if(!request.getParameter("auth").equals("admin")){
            response.sendRedirect("/"); // 루트경로 redirect (/ 경로로 redirect 하겠다는 의미)
            return false; // 요청 처리 중단
        }
        System.out.println("preHandle 호출됨");
        long startTime = System.currentTimeMillis(); // 요청 처리 시작 시간 기록
        // 처리 시간을 알아보기 위해 찍어봄 --- 성능처리
       request.setAttribute("startTime", startTime);
       // true 를 만나면 해당 매핑되는 컨트롤러 메소드를 호출한다는 얘기.
        // 컨트롤러를 이어서 호출한다.
       return true;
    }


    // 후처리 메소드 -- 지정된 컨트롤러의 동작 이후 처리할 동작 제어
    // 실행이 완료 되었찌만 아직 view 가 생성되기 전에 호출됨..
    // 디스패치 서블릿이 화면을 띄우기 전에 동작함. // 후처리 메소드이기 때문에 컨트롤러에서 보낸 modelAndView 에도 접근할 수 있다.
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mv) throws Exception {
        System.out.println("postHandle 호출함");
        System.out.println("모델 확인용" + mv.getModelMap());
        long startTime = (long)request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        mv.addObject("interval", endTime-startTime);
        // 얘는 리턴도 없으니까 그냥 정제하거나 담을거있으면 담고 뷰로 가는겨.
        // 컨트롤러의 모델과 interceptor 의 model 이 같은 모델이라는 뜻이다. 즉, 컨트롤러에서 모델에 담은걸 인터셉트에서 뽑을 수 있다.
    }


    // 모든 요청 처리가 완료된 후에 실행되는 메소드 // 자원을 정리해 주거나(close 처럼 닫는거?) 고론거..
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion 호출함 ... ");
    }
}

