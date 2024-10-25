package com.ohgiraffers.chap07fileupload;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class FileUploadController {

    @PostMapping("single-file")
    public String singleFile(@RequestParam MultipartFile singleFile,
                             String singleFileDescription, Model model){
        System.out.println("singleFile = "+singleFile);
        /*
        * StandardMultipartServletRequest
        *   - spring 에서 multipart 요청을 처리하기 위한 클래스
        * $StandardMultipartFile@2c1711e7
        *   - 업로드 된 파일을 나타내는 객체
        *   - 실제 파일 데이터에 대한 접근을 제공한다.*/
        System.out.println("singleFileDescription = "+singleFileDescription);

        // 파일을 저장할 경로 설정
        String filePath = "C:/uploads/single";
        File fileDir = new File(filePath);

        if(!fileDir.exists()){ // fileDir 이 경로가 실제로 존재하면 true
            fileDir.mkdirs(); // 경로가 없으면 생성한다.
        }

        // 사용자가 업로드한 원본 파일 이름 꺼내기.
        // 같은 이름의 파일일수도 있으니까 기존 이름에 고유한 무언가를 붙여주는것이다.
        String originFileName = singleFile.getOriginalFilename();

        // 확장자 추출 // 뒤부터 . 까지 추출한다. 즉 확장자만 담는다는 의미.// ex) sdf.png 에서 pmg
        String ext = originFileName.substring(originFileName.lastIndexOf("."));

        // UUID - 고유한 파일 이름을 막 ~ 생성해줄 메소드
        // UUID.randomUUID() - 고유 식별자 생성
        // replace("-", "") - 하이픈을 빼준다.(깔끔하게 문자열로 정리하기위해)
        // ext; - 뒤에 확장자 다시 붙여줌
        String savedName = UUID.randomUUID().toString().replace("-", "") + ext;

        // 업로드된 파일을 지정된 경로와 고유한 파일 이름으로 저장한다.
        try {
            singleFile.transferTo(new File(filePath + "/" + savedName));

            // 여기에 DB 저장 로직 추가하면 됨.

            model.addAttribute("message", "파일 업로드 성공 !");

            // img 라는 이름으로 경로를 담아준거.
            model.addAttribute("img", "/img/single/" + savedName);
        } catch (IOException e) {
           e.printStackTrace();
            model.addAttribute("message","파일 업로드 실패 !");
        }
        return "result";

    }

    @PostMapping("multi-file")
    public ModelAndView multifile(@RequestParam List<MultipartFile> multiFile,
                                  String multiFileDescription, ModelAndView mv) {

        String filePath = "C:/multiFile/multi";
        File fileDir = new File(filePath);
        List<String> membersList = new ArrayList<>();


        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

            try {

                for (MultipartFile file : multiFile) {
                    String originalFileName = file.getOriginalFilename();
                    String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
                    String savedName = UUID.randomUUID().toString().replace("-", "") + ext;
                    file.transferTo(new File(filePath + "/" + savedName));
                    membersList.add("/img/multi/" + savedName);
                    mv.setViewName("result");
                }

                mv.addObject("message", "성공 ~!");

            } catch (IOException e) {

                e.printStackTrace();
                mv.addObject("message", "실패 ~!");
                mv.setViewName("main");

            }
        mv.addObject("members", membersList);
        return mv;
        }

    }


