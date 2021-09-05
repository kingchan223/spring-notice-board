package com.community.service.member;

import com.community.domain.dto.member.JoinReqDto;
import com.community.domain.dto.member.MemberDto;
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
import java.util.ArrayList;
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
        Member member = Member.createMember(joinMemberForm,
                bCryptPasswordEncoder.encode(joinMemberForm.getPassword()));
        return memberRepository.save(member);
    }

    @Transactional
    public Member addMemberFromDto(JoinReqDto joinReqDto) {
        Member member = Member.createMemberFromJoinReqDto(joinReqDto, bCryptPasswordEncoder.encode(joinReqDto.getPassword()));
        return memberRepository.save(member);
    }

    @Override
    public Member findUser(Long id) {
        return memberRepository.findOne(id);
    }

    @Override
    public List<MemberDto> getAll() {
        List<MemberDto> memberDtos = new ArrayList<>();
        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            memberDtos.add(MemberDto.createMemberDto(member));
        }
        return memberDtos;
    }

    @Transactional
    @Override
    public Member addUserBasic(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Member login(String loginId, String password) {
        return memberRepository.login(loginId, password);
    }

    @Transactional
    @Override
    public Member changeUserInfo(Long id, EditMemberForm form) {
        Member member = memberRepository.findOne(id);
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
    public Member addRefreshToken(Long id, String refreshToken){
        Member member = memberRepository.findOne(id);
        member.setRefreshToken(refreshToken);
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
