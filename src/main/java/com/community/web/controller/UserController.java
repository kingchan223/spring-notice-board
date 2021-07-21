package com.community.web.controller;

import com.community.SessionConst;
import com.community.domain.entity.formEntity.JoinUserForm;
import com.community.domain.entity.formEntity.LoginUserForm;
import com.community.domain.entity.User;
import com.community.domain.entity.Writing;
import com.community.domain.entity.formEntity.editUserForm;
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
        List<User> userList = userService.getAll();
        for (User user : userList) {
            System.out.println("=======================");
            System.out.println(user.getUsername());
            System.out.println( user.getEmail());
            System.out.println(user.getPassword());
            System.out.println(user.getLoginId());
            System.out.println(user.getId());
        }
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

    /*로그아웃*/
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
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes redirectAttributes){
        log.info("bindingResult={}", bindingResult);
        if(bindingResult.hasErrors()){
//            log.info("bindingResult = {}", bindingResult);
            return "/basic/joinForm";
        }

        User savedUser = new User(joinUserForm.getUsername(), joinUserForm.getEmail(),
                joinUserForm.getPassword(), joinUserForm.getLoginId());

        userService.addUserBasic(savedUser);
        redirectAttributes.addAttribute("status", true);
//        LoginUserForm loginUserForm = new LoginUserForm(savedUser.getLoginId(), savedUser.getPassword());
//        model.addAttribute("loginUserForm", loginUserForm);
        return "redirect:/";
    }

    @GetMapping("/users")
    public String joinView(Model model){
        model.addAttribute("user", new User());
        return "/basic/joinForm";
    }

    @GetMapping("/userinfo")
    public String userInfo(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) User loginMember,
                           Model model){
        if(loginMember == null){
            return "redirect:/login";
        }
        model.addAttribute("user", loginMember);
        return "/basic/userInfo";
    }

    @GetMapping("/userinfo/edit")
    public String editUserInfoForm(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) User user,
                               Model model){
        if(user==null){
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "/basic/userInfoEditForm";
    }

    /*회원 정보 수정하기*/
    @PostMapping("/userinfo/edit")
    public String editUserInfo(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) User user,
                               HttpServletRequest request,
                               RedirectAttributes redirectAttributes,
                               @RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String loginId,
                               Model model){
        String ogLoginId = user.getLoginId();

        if(user==null){
            return "redirect:/login";
        }
        if((username==null) || (username.isEmpty())){
            username = user.getUsername();
        }
        if((email==null) || (email.isEmpty())){
            email = user.getEmail();
        }
        if((loginId==null) || (loginId.isEmpty())){
            loginId = user.getLoginId();
        }
        editUserForm newUserInfo = new editUserForm(username, email, loginId);
        User editedUser = userService.changeUserInfo(ogLoginId, newUserInfo);

        //아이디를 바꿨다면 세션을 삭제하고 다시 로그인 요청하기
        if(!ogLoginId.equals(username)){
            HttpSession session = request.getSession(false);
            if(session!=null){
                session.invalidate();
            }
            redirectAttributes.addAttribute("chanedId", true);
            return "redirect:/login";
        }

        model.addAttribute("user", editedUser);
        return "/basic/userInfo";
    }


}
