package com.community.web.controller;

import com.community.SessionConst;
import com.community.domain.entity.User;
import com.community.domain.entity.Writing;
import com.community.domain.entity.formEntity.AddWritingForm;
import com.community.service.interfaceService.WritingService;
import com.community.service.interfaceService.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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

        User writingUser = writingService.getOne(writingId).getUser();

        if(user.getEmail().equals(writingUser.getEmail()) && user.getUsername().equals(writingUser.getUsername())){
            model.addAttribute("right", true);
        }

        log.info("user.getEmail()={}", user.getEmail());
        log.info("writingUser.getEmail()={}", writingUser.getEmail());

        log.info("user.getUsername()={}", user.getUsername());
        log.info("writingUser.getUsername()={}", writingUser.getUsername());


        model.addAttribute("writing", writingService.getOne(writingId));
        return "/basic/writing";
    }

    /*글 수정 회면 보여주기*/
    @GetMapping("/edit/{writingId}")
    public String editView(@PathVariable Long writingId,Model model){
        Writing writing = writingService.getOne(writingId);
        model.addAttribute("writing", writing);
        return "/basic/writingEditForm";
    }

    /*글 수정*/
    @PostMapping("/edit/{writingId}")
    public String edit(@PathVariable Long writingId,
                       @Validated @ModelAttribute("writingForm") AddWritingForm addWritingForm){
        Writing afterWriting = new Writing(addWritingForm.getTitle(), addWritingForm.getContent());
        writingService.update(writingId, afterWriting);

        return "redirect:/";
    }

    /*글쓰기로 이동*/
    @GetMapping("writing")
    public String writingForm(@SessionAttribute(name= SessionConst.LOGIN_MEMBER, required = false) User user,
            @ModelAttribute("addWritingForm") AddWritingForm addWritingForm){
        if(user == null){
            return "redirect:/";
        }
//        log.info("user.name={}", user.getEmail());
//        log.info("user.name={}", user.getJoinedDate());
//        log.info("user.name={}", user.getId());
//        log.info("user.name={}", user.getLoginId());
//        log.info("user.name={}", user.getPassword());
//        log.info("user.name={}", user.getUsername());
//        log.info("user.name={}", user.getRole());
        return "/basic/writingForm";
    }

    /*글 작성*/
    @PostMapping("writing")
    public String writing(@Validated @ModelAttribute("writingForm") AddWritingForm addWritingForm,
                          BindingResult bindingResult,
                          HttpServletRequest request,
                          Model model,
                          RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            return "/basic/writingForm";
        }

        //유저찾기
        HttpSession session = request.getSession(false);
        User user = (User)session.getAttribute(SessionConst.LOGIN_MEMBER);

        //글 작성자에 유저 등록
        Writing savedWriting = new Writing(addWritingForm.getTitle(), addWritingForm.getContent());
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
