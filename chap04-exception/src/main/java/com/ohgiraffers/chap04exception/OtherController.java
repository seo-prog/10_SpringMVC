package com.ohgiraffers.chap04exception;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OtherController {

    @GetMapping("other-controller-null")
    public String therControllerNull() {
        String str = null;
        System.out.println(str.charAt(0));
        return "main";
    }

    @GetMapping("other-controller-user")
    public String otherControllerUser() throws MemberRegistException {
        if(true){
            throw new MemberRegistException("입사 불가");
        }
        return "main";
    }

    @GetMapping("other-controller-array")
    public String otherControllerArray() throws MemberRegistException {
        int[] arr = new int[0];
        System.out.println(arr[0]);
        return "main";
        // 없는 배열의 exception 에 접근하려 한다.
    }
}
