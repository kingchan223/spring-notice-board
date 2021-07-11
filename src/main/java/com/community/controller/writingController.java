package com.community.controller;

import com.community.SessionConst;
import com.community.entity.User;
import com.community.entity.Writing;
import com.community.entity.WritingForm;
import com.community.service.WritingService;
import com.community.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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


    /*글 상세*/
    @GetMapping("{writingId}")
    public String detail(@SessionAttribute(name= SessionConst.LOGIN_MEMBER, required = false) User user,
                         @PathVariable Long writingId, Model model){
        if(user == null){
            return "redirect:/";
        }
        model.addAttribute("writing", writingService.getOne(writingId));
        return "/basic/writing";
    }

    /*글쓰기로 이동*/
    @GetMapping("writing")
    public String writingForm(@SessionAttribute(name= SessionConst.LOGIN_MEMBER, required = false) User user,
            @ModelAttribute("writingForm") WritingForm writingForm){
        if(user == null){
            return "redirect:/";
        }
        return "/basic/writingForm";
    }

    /*글 작성*/
    @PostMapping("writing")
    public String writing(HttpServletRequest request,
                          @ModelAttribute("writingForm") WritingForm writingForm,
                          BindingResult bindingResult, Model model,
                          RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            return "/basic/writingForm";
        }


        //유저찾기
        HttpSession session = request.getSession(false);
        User user = (User)session.getAttribute(SessionConst.LOGIN_MEMBER);

        //글 작성자에 유저 등록
        Writing savedWriting = new Writing(writingForm.getTitle(), writingForm.getContent());
        savedWriting.setUser(user);
        //글 저장

        writingService.add(savedWriting);


        model.addAttribute("writings",writingService.getAll());
        model.addAttribute("user", user);

        redirectAttributes.addAttribute("writingId", savedWriting.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/{writingId}";
    }
}
