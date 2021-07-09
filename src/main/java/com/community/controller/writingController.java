package com.community.controller;

import com.community.SessionConst;
import com.community.entity.User;
import com.community.entity.Writing;
import com.community.service.WritingService;
import com.community.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class writingController {
    private final WritingService writingService;
    private final UserService userService;

    /*
    *  POST /writings : 글 생성
    *  GET /writings  :  글 생성 화면
    *  GET /writings/{writingNum}  : 글 상세조회
    *  POST /writings/{writingNum}/edit : 글 수정
    *  DELETE /writings/{writingNum} : 글 삭제
    */

    @GetMapping("{writingId}")
    public String detail(@SessionAttribute(name= SessionConst.LOGIN_MEMBER, required = false) User user,
                         @PathVariable Long writingId, Model model){
        if(user == null){
            return "redirect:/";
        }
        model.addAttribute("writing", writingService.getOne(writingId));
        return "/basic/writing";
    }

    @GetMapping("writing")
    public String writingForm(){
        return "/basic/writingForm";
    }

    @PostMapping("writing")
    public String writing(HttpServletRequest request, @ModelAttribute Writing writing, Model model){
        //유저찾기
        HttpSession session = request.getSession(false);
        User user = (User)session.getAttribute(SessionConst.LOGIN_MEMBER);

        //글 작성자에 유저 등록
        writing.setUser(user);
        //글 저장
        writingService.add(writing);


        model.addAttribute("writings",writingService.getAll());
        model.addAttribute("user", user);
        return "/basic/main";

    }
}
