package com.community.service.member;

import com.community.domain.entity.Member;
import com.community.domain.entity.formEntity.EditMemberForm;
import com.community.domain.entity.formEntity.JoinMemberForm;
import com.community.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Transient;
import java.util.List;

@Primary
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Transactional
    @Override
    public Member addUser(JoinMemberForm joinMemberForm) {
        Member member = Member.createMember(joinMemberForm, bCryptPasswordEncoder.encode(joinMemberForm.getPassword()));
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
    public Member changeUserInfo(Long loginId, EditMemberForm form) {
        Member member = memberRepository.findOne(loginId);
        member.changeMember(
                form.getName(),
                form.getEmail(),
                form.getLoginId(),
                form.getCity(),
                form.getStreet(),
                form.getZipcode());

        return member;
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
