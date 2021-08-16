package com.community.domain.dto.member;

import com.community.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberDto {
    private Long id;
    private String name;
    private String loginId;
    private String email;
    private String role;
    private String password;

    public static MemberDto createMemberDto(Long id, String name, String loginId, String email,String role){
        MemberDto memberDto = new MemberDto();
        memberDto.setId(id);
        memberDto.setName(name);
        memberDto.setEmail(email);
        memberDto.setLoginId(loginId);
        memberDto.setRole(role);
        memberDto.setPassword("1");
        return memberDto;
    }

    public static MemberDto createMemberDto(Member member){
        MemberDto memberDto = new MemberDto();
        memberDto.setId(member.getId());
        memberDto.setName(member.getName());
        memberDto.setEmail(member.getEmail());
        memberDto.setLoginId(member.getLoginId());
        memberDto.setRole(member.getRole());
        memberDto.setPassword("1");
        return memberDto;
    }
}
