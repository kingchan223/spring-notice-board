package com.community.service.member;

import com.community.domain.dto.member.JoinReqDto;
import com.community.domain.dto.member.MemberDto;
import com.community.domain.entity.Member;
import com.community.domain.entity.formEntity.EditMemberForm;
import com.community.domain.entity.formEntity.JoinMemberForm;

import java.util.List;

public interface MemberService {
    Member addUser(JoinMemberForm joinMemberForm);

    Member findUser(Long id);

    List<Member> getAll();

    Member addUserBasic(Member member);

    Member login(String loginId, String password);

    //아이디, 이름, 이메일 변경하기
    Member changeUserInfo(Long loginId, EditMemberForm newUser);

    void deleteUser(String loginId);

    Member findById(String loginId);

    Member addMemberFromDto(JoinReqDto joinReqDto);
}
