package com.community.web.controller;

import com.community.config.SessionConst;
import com.community.config.auth.PrincipalDetails;
import com.community.domain.entity.Member;
import com.community.domain.entity.RoleType;
import com.community.domain.entity.formEntity.EditMemberForm;
import com.community.domain.entity.formEntity.JoinMemberForm;
import com.community.domain.entity.formEntity.LoginMemberForm;
import com.community.domain.entity.Writing;
import com.community.service.writing.WritingService;
import com.community.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class MemberController {

    private final WritingService writingService;
    private final MemberService memberService;


    @GetMapping
    public String home(@AuthenticationPrincipal PrincipalDetails principal, Model model){
        String member = null;
        if(principal!=null){
            member = principal.getUsername();
            if(principal.getRole()== RoleType.MANAGER){

            }
            else if(principal.getRole()== RoleType.ADMIN){
                return "redirect:/admin";
            }
        }
        List<Writing> result = writingService.findAll();
        model.addAttribute("writings", result);
        model.addAttribute("member", member);
        return "main";
    }

//    @GetMapping("member/home")
//    public String loginHome(@AuthenticationPrincipal PrincipalDetails principal, Model model){
//        List<Writing> result = writingService.findAll();
//        model.addAttribute("writings", result);
//        return "/basic/main";
//    }

    @GetMapping("/loginForm")
    public String loginForm(Model model){
        model.addAttribute("loginMemberForm", new LoginMemberForm());
        return "loginForm";
    }

//    /*로그인 view*/
//    @GetMapping("login")
//    public String loginForm(@ModelAttribute("loginMemberForm") LoginMemberForm loginMemberForm,
//                            @RequestParam(defaultValue="/") String redirectURL){
//        return "/basic/loginForm";
//    }

//    /*로그인 ( 세션만들어준다. )*/
//    @PostMapping("login")
//    public String login(@Validated @ModelAttribute("loginMemberForm") LoginMemberForm loginMemberForm,
//                        BindingResult bindingResult,
//                        @RequestParam(defaultValue="/") String redirectURL,
//                        HttpServletRequest request){
//
////        log.info("bindingResult={}", bindingResult);
//        if(bindingResult.hasErrors()){
//            return "/basic/loginForm";
//        }
//
//        Member loginMember = memberService.login(loginMemberForm.getLoginId(), loginMemberForm.getPassword());
//
//        if(loginMember ==null){
//            bindingResult.reject("loginFail", "아이디 또는 비밀번호를 확인하세요.");
//            return "/basic/loginForm";
//        }
//
//        HttpSession session = request.getSession(true);
//        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
//        return "redirect:"+redirectURL;
//    }

//    /*로그아웃*/
//    @GetMapping("logout")
//    public String logout(HttpServletRequest request){
//        HttpSession session = request.getSession(false);
//        if(session!=null){
//            session.invalidate();
//        }
//        return "redirect:/";
//    }

    /*회원가입 view*/
    @GetMapping("join")
    public String join(@ModelAttribute JoinMemberForm joinMemberForm){
        return "joinForm";
    }


    /*회원가입*/
    @PostMapping("join")
    public String join(@Validated @ModelAttribute("joinUserForm") JoinMemberForm joinMemberForm,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes){
        log.info("bindingResult={}", bindingResult);
        if(bindingResult.hasErrors()){
            return "joinForm";
        }
        memberService.addUser(joinMemberForm);

        redirectAttributes.addAttribute("status", true);
        return "redirect:/";
    }

    @GetMapping("member/users")
    public String joinView(Model model){
        model.addAttribute("user", new Member());
        return "joinForm";
    }

    /*회원 정보보기*/
    @GetMapping("member/userinfo")
    public String userInfo(@AuthenticationPrincipal PrincipalDetails principal,
                           Model model){
        if(principal == null){
            return "redirect:/login";
        }
        model.addAttribute("member", memberService.findById(principal.getUsername()));
        return "/member/memberInfo";
    }
    /*회원 정보 수정 화면*/
    @GetMapping("member/userinfo/edit")
    public String editUserInfoForm(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member member,
                               Model model){
        if(member ==null){
            return "redirect:/login";
        }
        EditMemberForm editMemberForm = EditMemberForm.craeteEditUserForm(
                member.getName(),
                member.getEmail(),
                member.getLoginId(),
                member.getAddress().getCity(),
                member.getAddress().getStreet(),
                member.getAddress().getZipcode()
        );
        model.addAttribute("editMemberForm", editMemberForm);
        return "/member/memberInfoEditForm";
    }

    /*회원 정보 수정하기*/
    @PostMapping("member/userinfo/edit")
    public String editUserInfo(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member member,
                               HttpServletRequest request,
                               RedirectAttributes redirectAttributes,
                               EditMemberForm editMemberForm,
                               Model model){
        Long memberId = member.getId();

        if(member ==null){
            return "redirect:/login";
        }
        Member editMember = memberService.changeUserInfo(memberId, editMemberForm);
        model.addAttribute("member", editMember);
        return "/member/memberInfo";
    }
}
