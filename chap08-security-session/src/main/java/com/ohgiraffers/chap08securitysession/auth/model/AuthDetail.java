package com.ohgiraffers.chap08securitysession.auth.model;

import com.ohgiraffers.chap08securitysession.user.dto.LoginUserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// 우리가 사용할 메소드 아니라 이 메소드들도 시큐리티에서 사용할 메소드.

public class AuthDetail  implements UserDetails {

    private LoginUserDTO loginUserDTO;

    public AuthDetail() {
    }

    public AuthDetail(LoginUserDTO loginUserDTO) {
        this.loginUserDTO = loginUserDTO;
    }

    public LoginUserDTO getLoginUserDTO() {
        return loginUserDTO;
    }

    public void setLoginUserDTO(LoginUserDTO loginUserDTO) {
        this.loginUserDTO = loginUserDTO;
    }

    //  권한 정보 반환 메소드 확인
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       Collection<GrantedAuthority> authorities = new ArrayList<>();
       for(String role : loginUserDTO.getRole()){
           authorities.add(new GrantedAuthority() {
               @Override
               public String getAuthority() {
                   return role;

               }
           }
           );
       }
       return authorities;
    }

    // 사용자의 비밀번호를 반환하는 메소드
    @Override
    public String getPassword() {
        return loginUserDTO.getPassword();
    }

    // 사용자의 아이디를 반환하는 메소드
    @Override
    public String getUsername() {
        return loginUserDTO.getUserId();
    }
    // 사용자가 입력한 값을 담아온다. 그걸 시큐리티가 알아서 db 값이랑 입력값이랑 비교하려는 메소드들. // 위에 두 메소드


    // 계정 만료 여부를 표현하는 메소드 - false 이면 해당 계정을 사용할 수 없다.
    // 계정을 만료시킬 수 있는 메소드.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    // 잠겨있는 계정을 확인하는 메소드 - false 면 사용할 수 없다.
    // 오래 사용하지 않았을 때, 휴먼계정등 을 여기서 설정해줄 수 있다.
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    // 탈퇴 계정 여부 표현 메소드 -  false 면 사용할 수 없다.
    // 탈퇴가 delete 가 아니라 ( 적책상 몇달 뒤) false 로 주면 로그인 불가능!
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    // 계정 비활성화로 사용자가 사용 못하는 경우
    @Override
    public boolean isEnabled() {
        return true;
    }
}
