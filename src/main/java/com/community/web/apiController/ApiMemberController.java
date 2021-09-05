package com.community.web.apiController;

import com.community.domain.dto.CMRespDto;
import com.community.domain.dto.member.JoinReqDto;
import com.community.domain.dto.member.MemberDto;
import com.community.domain.entity.Member;
import com.community.domain.entity.formEntity.EditMemberForm;
import com.community.domain.entity.formEntity.LoginMemberForm;
import com.community.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ApiMemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/api/join")
    public ResponseEntity<?> join(@RequestBody JoinReqDto joinReqDto){
        //System.out.println("memberDto = " + memberDto.toString());
        return new ResponseEntity<>(memberService.addMemberFromDto(joinReqDto), HttpStatus.CREATED);
    }

    // memberInfo
    @GetMapping("/api/member")
    public ResponseEntity<?> memberinfo(HttpServletRequest request) {
        System.out.println("userinfo 호출됨");
        Long memberId = (Long) request.getAttribute("memberId");
        Member member = memberService.findUser(memberId);
        MemberDto memberDto = MemberDto.createMemberDto(member);
        return new ResponseEntity<>(new CMRespDto<MemberDto>(1, "success", memberDto), HttpStatus.OK);
    }

    // 회원 수정
    @PutMapping("/api/member/{id}")
    public ResponseEntity<?> editMemberInfo(@PathVariable Long id, @RequestBody EditMemberForm editMemberForm){
        System.out.println("회원정보 수정 발동");
        Member member = memberService.changeUserInfo(id, editMemberForm);
        MemberDto memberDto = MemberDto.createMemberDto(member);
        return new ResponseEntity<>(new CMRespDto<MemberDto>(1, "success", memberDto), HttpStatus.OK);
    }

    @GetMapping("/api/allmember")
    public List<MemberDto> getAllMember(){
        return memberService.getAll();
    }
}
