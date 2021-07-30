package com.community.service.interfaceService;

import com.community.domain.entity.Member;
import com.community.domain.entity.formEntity.EditUserForm;
import com.community.domain.entity.formEntity.JoinMemberForm;

import java.util.List;

public interface MemberService {
    Member addUser(JoinMemberForm joinMemberForm);

    Member findUser(Long id);

    List<Member> getAll();

    Member addUserBasic(Member member);

    Member login(String loginId, String password);

    //아이디, 이름, 이메일 변경하기
    void changeUserInfo(String loginId, EditUserForm newUser);

    void deleteUser(String loginId);

    Member findById(String loginId);
}
