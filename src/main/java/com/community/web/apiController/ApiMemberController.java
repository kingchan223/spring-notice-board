package com.community.web.apiController;

import com.community.domain.dto.CMRespDto;
import com.community.domain.dto.member.MemberDto;
import com.community.domain.entity.Member;
import com.community.domain.entity.formEntity.LoginMemberForm;
import com.community.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ApiMemberController {

    private final MemberService memberService;

//    @PostMapping("/api/login")
//    public ResponseEntity<?> login(@RequestBody LoginMemberForm loginForm){
//        System.out.println("/api/login 실헹");
//        Member member = memberService.login(loginForm.getLoginId(), loginForm.getPassword());
//        if(member==null){
//            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(  MemberDto.createMemberDto(member),HttpStatus.OK);
//    }

    @PostMapping("/api/join")
    public ResponseEntity<?> join(@RequestBody MemberDto memberDto){
        //System.out.println("memberDto = " + memberDto.toString());
        return new ResponseEntity<>(memberService.addMemberFromDto(memberDto), HttpStatus.CREATED);
    }

    @GetMapping("/api/member")
    public ResponseEntity<?> userinfo(HttpServletRequest request) {
        System.out.println("userinfo 호출됨");
        Member member = memberService.findUser((Long) request.getAttribute("id"));
        return new ResponseEntity<>(new CMRespDto<Member>(1, "success", member), HttpStatus.OK);
    }
}
