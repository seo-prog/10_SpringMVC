package com.ohgiraffers.chap08securitysession.config;


import com.ohgiraffers.chap08securitysession.common.UserRole;
import com.ohgiraffers.chap08securitysession.config.handler.AuthFailHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.PassThroughSourceExtractor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
// 시큐리티에 리소스를 관리하는 bean 들을 모아두고 관리하는 곳 !
// 이건 우리가 쓸게 아님. 시큐리티가 쓸거임. 즉, 동작이 어떻게 되는지 몰라도 된다.
public class SecurityConfig {

    @Autowired
    private AuthFailHandler authFailHandler;

    // 비밀번호 인코딩 Bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 대중적인 비밀번호 암호화 알고리즘
    }

    // 정적 리소스 요청 제외 Bean // 정적 데이터들은 메모리만 잡아먹고 시큐리티에 들어갈 필욛가 없으니 빼주겠다.
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web-> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    // 필터 체인 커스텀
    // 여러  요청이 들어올껀데 어떻게 처리를 해줄꺼냐는 뜻

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth->{ // 서버의 리소스에 접근 가능한 권한 설정 // auth 는 실행 반환물이 된다.
            // 이 요청들은 모든 사용자에게 허용 - 인증 필요 없음 !
            auth.requestMatchers("/auth/login", "/user/signup", "/auth/fail", "/").permitAll();

            // Role_admin 에게만 허용하겠다.
            auth.requestMatchers("/admin/*").hasAnyAuthority(UserRole.ADMIN.getRole());

            // Role_user 에게만 허용하겠다.
            auth.requestMatchers("/user/*").hasAnyAuthority(UserRole.USER.getRole());
            auth.anyRequest().authenticated(); // 모든 요청을 인증된 사용자에게 허용해주겠다.(위 내용은 제외하고)

        }).formLogin(login ->{

            login.loginPage("/auth/login"); // 여기로 post 요청이 날라왔을 때, 시큐리티가 처리해준다는 내용.
            // 대신 해당하는 페이지와 메소드, 매핑은 있어야 한다. (get 요청도 있을 시 !)
            login.usernameParameter("user");
            login.passwordParameter("password"); // html input name 값이다.
            login.defaultSuccessUrl("/"); // 로그인 성공 시 보낼 곳 설정.. mapping 이 존재해야함..
            login.failureHandler(authFailHandler); // 실패 시 처리

        }).logout(logout->{

            // 로그아웃 시 요청을 날릴 url 설정 // 이건 페이지 만들 필요 없음. 시쿠리티가 알아서 처리.
            logout.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"));

            // 서버가 사용자에게 세션을 발급해주면 JSESSIONID 가 자동으로 생성이 되게 되는데,
            // JSESSIONID 을 지우면 더이상 세션을 쓸 수 없게 된다.
            // 사용자가 세션을 쓰지 못하게 제거
             logout.deleteCookies("JSESSIONID");

             logout.invalidateHttpSession(true); // 세션이 소멸하는 걸 허용하는 메소드
            logout.logoutSuccessUrl("/"); // 로그아웃 완료 후 이동할 페이지 설정

        }).sessionManagement(session->{ // 세션을 컨드롤하기 위한 마지막 메소드 체이닝

            session.maximumSessions(1);// 세션의 객수 제한 1로 설정 시 중복 로그인 x.// 한 계정은 하나의 디바이스 에서만 가능하다는 뜻.

            session.invalidSessionUrl("/");// 세션 만료 시 이동할 페이지
        }).csrf(csrf -> csrf.disable());
        // 사용자가 의도하지 않는 공격을 날리는거. 이걸 처리해주지 않겠다는 뜻 ! csrf 처리 안함이라는 뜻// 우리는 당장 사용 안할꺼니까 없애줌.
        return http.build();
    }
}
