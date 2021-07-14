package com.community.web.controller;

import com.community.SessionConst;
import com.community.domain.entity.formEntity.JoinUserForm;
import com.community.domain.entity.formEntity.LoginUserForm;
import com.community.domain.entity.User;
import com.community.domain.entity.Writing;
import com.community.service.interfaceService.WritingService;
import com.community.service.interfaceService.UserService;
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

    /*로그인 view*/
    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginUserForm") LoginUserForm loginUserForm,
                            @RequestParam(defaultValue="/") String redirectURL){
        log.info("GET redirectUrl={}", redirectURL);
        return "/basic/loginForm";
    }

    /*로그인 ( 세션만들어준다. )*/
    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("loginUserForm") LoginUserForm loginUserForm,
                        BindingResult bindingResult,
                        @RequestParam(defaultValue="/") String redirectURL,
                        HttpServletRequest request){

        log.info("bindingResult={}", bindingResult);
        if(bindingResult.hasErrors()){
            return "/basic/loginForm";
        }

        User loginUser = userService.login(loginUserForm.getLoginId(),  loginUserForm.getPassword());

        if(loginUser==null){
            bindingResult.reject("loginFail", "아이디 또는 비밀번호를 확인하세요.");
            return "/basic/loginForm";
        }

        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginUser);
        log.info("loginUser={}", loginUser);
        log.info("POST redirectUrl={}", redirectURL);
        return "redirect:"+redirectURL;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session!=null){
            session.invalidate();
        }
        return "redirect:/";
    }

    /*회원가입 view*/
    @GetMapping("/join")
    public String join(@ModelAttribute("joinUserForm") JoinUserForm joinUserForm){
        return "/basic/joinForm";
    }


    /*회원가입*/
    @PostMapping("/join")
    public String join(@Validated @ModelAttribute("joinUserForm") JoinUserForm joinUserForm,
                       BindingResult bindingResult, Model model){
        log.info("bindingResult={}", bindingResult);
        if(bindingResult.hasErrors()){
//            log.info("bindingResult = {}", bindingResult);
            return "/basic/joinForm";
        }

        User savedUser = new User(joinUserForm.getUsername(), joinUserForm.getEmail(),
                joinUserForm.getPassword(), joinUserForm.getLoginId());

        userService.addUserBasic(savedUser);
        model.addAttribute("param", true);
        LoginUserForm loginUserForm = new LoginUserForm(savedUser.getLoginId(), savedUser.getPassword());
        model.addAttribute("loginUserForm", loginUserForm);
        return "/basic/loginForm";
    }

    @GetMapping("/users")
    public String joinView(Model model){
        model.addAttribute("user", new User());
        return "/basic/joinForm";
    }
}
