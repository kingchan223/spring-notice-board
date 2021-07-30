package com.community.service.member;

import com.community.domain.entity.Member;
import com.community.domain.entity.formEntity.EditUserForm;
import com.community.domain.entity.formEntity.JoinMemberForm;
import com.community.repository.member.MemberRepository;
import com.community.service.interfaceService.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Primary
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;


    @Transactional
    @Override
    public Member addUser(JoinMemberForm joinMemberForm) {
        Member member = Member.createMember(joinMemberForm);
        return memberRepository.save(member);
    }

    @Override
    public Member findUser(Long id) {
        return memberRepository.findOne(id);
    }

    @Override
    public List<Member> getAll() {
        return memberRepository.findAll();
    }

    @Override
    public Member addUserBasic(Member member) {
        return null;
    }

    @Override
    public Member login(String loginId, String password) {
        return memberRepository.login(loginId, password);
    }

    @Transactional
    @Override
    public void changeUserInfo(String loginId, EditUserForm newUser) {

    }

    @Transactional
    @Override
    public void deleteUser(String loginId) {

    }

    @Override
    public Member findById(String loginId) {
        return memberRepository.findByLoginId(loginId);
    }
}
