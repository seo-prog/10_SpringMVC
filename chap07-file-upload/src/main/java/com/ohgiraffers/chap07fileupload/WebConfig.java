package com.ohgiraffers.chap07fileupload;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    // WebMvcConfigurer - 우리 인터셉터 할 떄 썼던 인터페이스

    // 정적 자원을 처리하기 위한 메소드

    // 나중에 파일 경로가 바뀌어도 편하게 경로만 바꿔줄 수 있도록 편하게 모아서 쓰는겨.

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 여기서 설정을 해두면 디스패처 서블릿에서 시키는데로 하지 말고 이대로 해라!
        // /img/single/** 의 요청들은 다 여기서 처리하겠다는 뜻
        registry.addResourceHandler("/img/multi/**")
                .addResourceLocations("file:///C:/multiFile/multi/");
        // 위에 요청이 들어왔을 때, file:///C:/uploads/single/ 이 경로로 응답해줘라 ! 라는 뜻(디스패처 말고)
    }
}
