package com.ohgiraffers.chap08securitysession.user.dto;


import com.ohgiraffers.chap08securitysession.common.UserRole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 로그인 시 security 가 사용할 dto
public class LoginUserDTO {

    private int userCode;
    private String userId;
    private String userName;
    private String password;
    private UserRole userRole;

    public LoginUserDTO() {
    }

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public List<String> getRole(){

        // 우리는 하나밖에 없지만 권한을 여러개 가지는 곳이 많기에 시큐리티가 기본적으로 list 로 받기에 우리도 고려하여 list 로 넘겨준거다.
        // userRole 안에있는 method 실행 얘기임

        if(this.userRole.getRole().length() > 0){
            return Arrays.asList(this.userRole.getRole().split(","));
        }
            return new ArrayList<>();

    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "LoginUserDTO{" +
                "userCode=" + userCode +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}
