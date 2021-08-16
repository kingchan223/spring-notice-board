package com.community.web.apiController;

import com.community.domain.dto.member.MemberDto;
import com.community.domain.entity.Member;
import com.community.domain.entity.formEntity.LoginMemberForm;
import com.community.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ApiMemberController {

    private final MemberService memberService;

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody LoginMemberForm loginForm){
        Member member = memberService.login(loginForm.getLoginId(), loginForm.getPassword());
        if(member==null){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(member,HttpStatus.OK);
    }

    @PostMapping("/api/join")
    public ResponseEntity<?> join(@RequestBody MemberDto memberDto){
        //System.out.println("memberDto = " + memberDto.toString());
        return new ResponseEntity<>(memberService.addMemberFromDto(memberDto), HttpStatus.CREATED);
    }


}
