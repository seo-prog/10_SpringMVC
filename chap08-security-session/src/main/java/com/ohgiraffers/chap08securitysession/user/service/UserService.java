package com.ohgiraffers.chap08securitysession.user.service;

import com.ohgiraffers.chap08securitysession.user.dao.UserMapper;
import com.ohgiraffers.chap08securitysession.user.dto.LoginUserDTO;
import com.ohgiraffers.chap08securitysession.user.dto.SignupDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Service
public class UserService {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    // 우리가 bean 으로 구현해둔 걔가 들어올꺼다.
    // SecurityConfig 에 구현해둠,
    private PasswordEncoder encoder;


    // Transactional - 메소드가 정상적으로 완료되면 커밋함. 실행 중 예외 발생 시 롤백함
    @Transactional
    // 여기가 트랜잭션 책임지는 부분이야 라고 표시해준거.
    public int regist(SignupDTO signupDTO) {
        if (signupDTO.getUserId() == null || signupDTO.getUserId().isEmpty()) {
            return 0;
        }
        if (signupDTO.getUserName() == null || signupDTO.getUserName().isEmpty()) {
            return 0;
        }

        if (signupDTO.getPassword() == null || signupDTO.getPassword().isEmpty()) {
            return 0;
        }
        signupDTO.setPassword(encoder.encode(signupDTO.getPassword()));
        int result = userMapper.regist(signupDTO);
        return result;
    }

    // username - 식별자 이므로 우리의 db 에는 id 이다.
    public LoginUserDTO findByUserName(String username) {
        System.out.println(username);

        LoginUserDTO login = userMapper.findByUserName(username);
        if(Objects.isNull(login)){
            return null;
        }else{
            return login;
        }
    }
}
