package com.community.web.controller;

import com.community.config.SessionConst;
import com.community.config.auth.PrincipalDetails;
import com.community.domain.entity.Board;
import com.community.domain.entity.Member;
import com.community.domain.entity.formEntity.EditMemberForm;
import com.community.domain.entity.formEntity.JoinMemberForm;
import com.community.domain.entity.formEntity.LoginMemberForm;
import com.community.service.board.BoardService;
import com.community.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
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

    private final BoardService boardService;
    private final MemberService memberService;

    @GetMapping
    public String home(@AuthenticationPrincipal PrincipalDetails principal, Model model){
        String member = null;
        if(principal!=null){
            member = principal.getName();
            if(principal.getRole()== "MANAGER"){

            }
            else if(principal.getRole()=="ADMIN"){
                return "redirect:/admin";
            }
        }
        List<Board> result = boardService.findAll();
        model.addAttribute("boards", result);
        model.addAttribute("member", member);
        return "rooms";
    }

//    @GetMapping("member/home")
//    public String loginHome(@AuthenticationPrincipal PrincipalDetails principal, Model model){
//        List<Board> result = BoardService.findAll();
//        model.addAttribute("boards", result);
//        return "/basic/main";
//    }

    @ResponseBody
    @GetMapping("test/oauth/login")
    public String loginOauthTest(Authentication authentication,
                                 @AuthenticationPrincipal OAuth2User oauth){

        //OAuth?????? User ????????? ?????? ?????? 1. : Authentication ????????? ?????? ??????
        /* Oauth??? OAuth2User ???????????? ?????? ????????? ???????????????. */
        OAuth2User oauth2User = (OAuth2User)authentication.getPrincipal();
        System.out.println("authentication = " + oauth2User.getAttributes());

        //OAuth?????? User ????????? ?????? ?????? 2. : @AuthenticationPrincipal ??????
        System.out.println("oauth = " + oauth.getAttributes());

        return "OAuth ?????? ?????? ????????????";
    }


    @GetMapping("/loginForm")
    public String loginForm(Model model){
        model.addAttribute("loginMemberForm", new LoginMemberForm());
        return "loginForm";
    }

//    /*????????? view*/
//    @GetMapping("login")
//    public String loginForm(@ModelAttribute("loginMemberForm") LoginMemberForm loginMemberForm,
//                            @RequestParam(defaultValue="/") String redirectURL){
//        return "/basic/loginForm";
//    }

//    /*????????? ( ?????????????????????. )*/
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
//            bindingResult.reject("loginFail", "????????? ?????? ??????????????? ???????????????.");
//            return "/basic/loginForm";
//        }
//
//        HttpSession session = request.getSession(true);
//        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
//        return "redirect:"+redirectURL;
//    }

//    /*????????????*/
//    @GetMapping("logout")
//    public String logout(HttpServletRequest request){
//        HttpSession session = request.getSession(false);
//        if(session!=null){
//            session.invalidate();
//        }
//        return "redirect:/";
//    }

    /*???????????? view*/
    @GetMapping("join")
    public String join(@ModelAttribute JoinMemberForm joinMemberForm){
        return "joinForm";
    }


    /*????????????*/
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

    /*?????? ????????????*/
    @GetMapping("member/userinfo")
    public String userInfo(@AuthenticationPrincipal PrincipalDetails principal,
                           Model model){
        if(principal == null){
            return "redirect:/login";
        }
        model.addAttribute("member", memberService.findById(principal.getUsername()));
        return "/member/memberInfo";
    }
    /*?????? ?????? ?????? ??????*/
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

    /*?????? ?????? ????????????*/
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
