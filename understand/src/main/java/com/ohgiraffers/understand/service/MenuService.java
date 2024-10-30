package com.ohgiraffers.understand.service;

import com.ohgiraffers.understand.dto.MenuDTO;
import com.ohgiraffers.understand.exception.NotInsertNameException;
import com.ohgiraffers.understand.model.MenuDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuDAO menuDAO;


    public List<MenuDTO> selectAllMenu() {

        List<MenuDTO> menus = menuDAO.selectAllMenu();
        // 데이터가 잘 들왔는지 여기서 한번 더 체크 해도 좋음 !
        return menus;

    }

    public List<MenuDTO> selectMenuByCode(int code) {

        List<MenuDTO> menus = menuDAO.selectMenuByCode(code);
        return menus;
    }

    public int regist(MenuDTO menuDTO) throws NotInsertNameException {

        // 중복되는 메뉴 이름 있는지 확인
        List<MenuDTO> menus = menuDAO.selectAllMenu();
        // 이름만 불러오는 요청 만들어서 날리는게 더 좋다.

        List<String> names = new ArrayList<>();

        for(MenuDTO menu : menus) {
            names.add(menu.getName());
        }
        if(names.contains(menuDTO.getName()) || menuDTO.getName().isEmpty()) { // 비어있어도 에러 던질꺼다.
            throw new NotInsertNameException(""); // try catch 하지말고 던져라 걍. 우리가 만든거로.
        }
        if(menuDTO.getPrice() <= 0){
            return 0;
        }
        int result = menuDAO.regist(menuDTO);
        return result;
    }
}
