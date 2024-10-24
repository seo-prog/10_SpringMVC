package com.ohgiraffers.chap05interceptor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // spring 의 bean 설정 클래스
public class WebConfiguration implements WebMvcConfigurer {
    // 가장 먼저 본다. 요청 이후에
                                         // spring mvc 설정 추가 용도

    @Autowired
    private StopWatchInterceptor stopWatchInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //"/stopwatch" 이 요청이 들어왔을때는 stopWatchInterceptor 이 인터셉트로 보내라는 뜻.
        // 인터셉터 등록이 되어있으면 보내준다는것이다.
       registry.addInterceptor(stopWatchInterceptor)
               // /stopwatch 경로에 등록한 인터셉터 적용
               .addPathPatterns("/stopwatch") // 인터셉터가 실행될 경로 // 보통 경로를 설정해준다.
               .excludePathPatterns("/css/**")
               .excludePathPatterns("/js/**")
               .excludePathPatterns("/images/**");
       // 필요없는 정적인 파일들은 제외시켜준다. 불필요한 동작들이 들어갈 수 있기 때문에..


    }
}
