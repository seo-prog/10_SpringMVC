package com.ohgiraffers.understand.controller;


import com.ohgiraffers.understand.dto.MenuDTO;
import com.ohgiraffers.understand.exception.NotInsertNameException;
import com.ohgiraffers.understand.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/menus/*")
public class MenuController {

    @Autowired
    private MenuService menuService;


    @GetMapping("menus")
    public ModelAndView selectAllMenu(ModelAndView mv) {
        List<MenuDTO> menus = menuService.selectAllMenu();

        if(Objects.isNull(menus)) {
            throw new NullPointerException();
        }
        mv.addObject("menus", menus);
        mv.setViewName("menus/allMenus");
        return mv;
    }

    @GetMapping("onemenue")
    public ModelAndView selectOneMenu(ModelAndView mv) {
        mv.setViewName("menus/oneMenu");
        return mv;
    }


    @GetMapping("onemenuaction")
    public ModelAndView selectOneMenuAction(ModelAndView mv, MenuDTO menu) {

        // 로직
        // requestParam 이용하거나 dto 이용 ! 스프링에서 제공하는 서비스.
        // 넘어오는 값이(code) 같은 이름의 값이 필드에 있다면, 자동 바인딩을 해준다는 뜻.
        // menu dto 로 쓰면 자동으로 그 자료형으로 변환해준다.
        int code = menu.getCode();
        System.out.println(code);
        List<MenuDTO> menus = menuService.selectMenuByCode(code);
        System.out.println(menus);

        if(Objects.isNull(menus)) {
            throw new NullPointerException();
        }
        mv.addObject("menus", menus);
        mv.setViewName("menus/allMenus");
        return mv;
    }

    @GetMapping("regist")
    public ModelAndView insert(ModelAndView mv) {
        mv.setViewName("menus/regist");
        return mv;
    }

    @PostMapping("regist")
    public ModelAndView insertMenu(ModelAndView mv, MenuDTO menuDTO ) throws NotInsertNameException {

        int regist = menuService.regist(menuDTO);
        System.out.println(regist);
        System.out.println(menuDTO);

        if(regist <= 0 ){
            mv.addObject("message", "가격은 음수일 수 없습니다!");
            mv.setViewName("/error/errorMesage");
        }else{
            mv.setViewName("/menus/returnMessage");
        }

        return mv;
    }


}
