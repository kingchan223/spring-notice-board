package com.community.controller;

import com.community.SessionConst;
import com.community.entity.LoginUserForm;
import com.community.entity.User;
import com.community.entity.Writing;
import com.community.service.WritingService;
import com.community.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping
public class UserController {

    private final WritingService writingService;
    private final UserService userService;


    /* 홈화면*/
    @GetMapping("/")
    public String home(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) User loginMember, Model model){
        if(loginMember==null){
            return "redirect:/login";
        }

        List<Writing> result = writingService.getAll();
        model.addAttribute("writings", result);
        model.addAttribute("user", loginMember);
        return "/basic/main";
    }

    @GetMapping("/login")
    public String loginForm(){
        return "/basic/loginForm";
    }

    /*로그인*/
    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginUserForm form, BindingResult bindingResult
                        ,HttpServletRequest request){

        if(bindingResult.hasErrors()){
            return "/basic/loginForm";
        }

        User loginUser = userService.login(form.getLoginId(), form.getPassword());

        if(loginUser==null){
            bindingResult.reject("loginFail", "아이디 또는 비밀번호를 확인하세요.");
            return "/basic/loginForm";
        }

        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginUser);
        log.info("loginUser={}", loginUser);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session!=null){
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/join")
    public String join(){
        return "/basic/joinForm";
    }


    /*회원가입*/
    @PostMapping("/join")
    public String join(@Validated @ModelAttribute User user, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
//            log.info("bindingResult = {}", bindingResult);
            return "/basic/joinForm";
        }

        userService.addUserBasic(user);
        model.addAttribute("param", true);
        return "/basic/loginForm";
    }

    @GetMapping("/users")
    public String joinView(Model model){
        model.addAttribute("user", new User());
        return "/basic/joinForm";
    }
}
