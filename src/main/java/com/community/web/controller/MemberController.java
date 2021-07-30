package com.community.web.controller;

import com.community.SessionConst;
import com.community.domain.entity.Member;
import com.community.domain.entity.formEntity.JoinMemberForm;
import com.community.domain.entity.formEntity.LoginMemberForm;
import com.community.domain.entity.Writing;
import com.community.domain.entity.formEntity.EditUserForm;
import com.community.service.interfaceService.WritingService;
import com.community.service.interfaceService.MemberService;
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
@RequestMapping("/")
public class MemberController {

    private final WritingService writingService;
    private final MemberService memberService;


    /* 홈화면*/
    @GetMapping
    public String home(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model){
        if(loginMember==null){
            return "redirect:/login";
        }

        List<Writing> result = writingService.findAll();
        model.addAttribute("writings", result);
        model.addAttribute("member", loginMember);
        return "/basic/main";
    }

    /*로그인 view*/
    @GetMapping("login")
    public String loginForm(@ModelAttribute("loginMemberForm") LoginMemberForm loginMemberForm,
                            @RequestParam(defaultValue="/") String redirectURL){
        List<Member> memberList = memberService.getAll();
        return "/basic/loginForm";
    }

    /*로그인 ( 세션만들어준다. )*/
    @PostMapping("login")
    public String login(@Validated @ModelAttribute("loginMemberForm") LoginMemberForm loginMemberForm,
                        BindingResult bindingResult,
                        @RequestParam(defaultValue="/") String redirectURL,
                        HttpServletRequest request){

//        log.info("bindingResult={}", bindingResult);
        if(bindingResult.hasErrors()){
            return "/basic/loginForm";
        }

        Member loginMember = memberService.login(loginMemberForm.getLoginId(), loginMemberForm.getPassword());

        if(loginMember ==null){
            bindingResult.reject("loginFail", "아이디 또는 비밀번호를 확인하세요.");
            return "/basic/loginForm";
        }

        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        return "redirect:"+redirectURL;
    }

    /*로그아웃*/
    @GetMapping("logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session!=null){
            session.invalidate();
        }
        return "redirect:/";
    }

    /*회원가입 view*/
    @GetMapping("join")
    public String join(@ModelAttribute("joinUserForm") JoinMemberForm joinMemberForm){
        return "/basic/joinForm";
    }


    /*회원가입*/
    @PostMapping("join")
    public String join(@Validated @ModelAttribute("joinUserForm") JoinMemberForm joinMemberForm,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes){
        log.info("bindingResult={}", bindingResult);
        if(bindingResult.hasErrors()){
//            log.info("bindingResult = {}", bindingResult);
            return "/basic/joinForm";
        }
        memberService.addUser(joinMemberForm);

        redirectAttributes.addAttribute("status", true);
        return "redirect:/";
    }

    @GetMapping("users")
    public String joinView(Model model){
        model.addAttribute("user", new Member());
        return "/basic/joinForm";
    }

    @GetMapping("userinfo")
    public String userInfo(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                           Model model){
        if(loginMember == null){
            return "redirect:/login";
        }
        model.addAttribute("user", loginMember);
        return "MemberInfo";
    }

    @GetMapping("userinfo/edit")
    public String editUserInfoForm(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member member,
                               Model model){
        if(member ==null){
            return "redirect:/login";
        }
        model.addAttribute("user", member);
        return "memberInfoEditForm";
    }

    /*회원 정보 수정하기*/
    @PostMapping("userinfo/edit")
    public String editUserInfo(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member member,
                               HttpServletRequest request,
                               RedirectAttributes redirectAttributes,
                               @RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String loginId,
                               Model model){
        String ogLoginId = member.getLoginId();

        if(member ==null){
            return "redirect:/login";
        }
        if((username==null) || (username.isEmpty())){
            username = member.getName();
        }
        if((email==null) || (email.isEmpty())){
            email = member.getEmail();
        }
        if((loginId==null) || (loginId.isEmpty())){
            loginId = member.getLoginId();
        }

        EditUserForm editUserForm = new EditUserForm(username, email, loginId);
        memberService.changeUserInfo(ogLoginId, editUserForm);

        //아이디를 바꿨다면 세션을 삭제하고 다시 로그인 요청하기
        if(!ogLoginId.equals(loginId)){
            HttpSession session = request.getSession(false);
            if(session!=null){
                session.invalidate();
            }
            redirectAttributes.addAttribute("changedId", true);
            return "redirect:/login";
        }

        model.addAttribute("user", editUserForm);
        return "MemberInfo";
    }
}
