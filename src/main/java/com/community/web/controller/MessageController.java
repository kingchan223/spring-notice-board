package com.community.web.controller;

import com.community.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequiredArgsConstructor
@Controller
@RequestMapping("/message/")
public class MessageController {

    private final MemberService memberService;


    @GetMapping("memberList")
    public String memberList(Model model){
        model.addAttribute("members", memberService.getAll());
        return "/message/memberList";
    }

}
