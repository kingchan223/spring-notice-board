package com.community.service.member;


import com.community.domain.dto.member.JoinReqDto;
import com.community.domain.dto.member.MemberDto;
import com.community.domain.entity.Member;
import com.community.domain.entity.formEntity.EditMemberForm;
import com.community.domain.entity.formEntity.JoinMemberForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class MemoryMemberServiceImpl implements MemberService {

    private Map<Long, Member> store = new ConcurrentHashMap<>();
    private Long squence = 0L;

    /*회원 저장*/
    public Member addUser(JoinMemberForm joinMemberForm){
        Long id = squence++;
        Member member = new Member(joinMemberForm.getName(), joinMemberForm.getEmail(), joinMemberForm.getPassword(), joinMemberForm.getLoginId(), id);
        store.put(id, member);
        return member;
    }

    /*회원 객체로 회원 저장*/
    public Member addUserBasic(Member member){
        store.put(member.getId(), member);
        return member;
    }

    /*아이디, 패스워드로 로그인하기*/
    @Override
    public Member login(String loginId, String password) {
        Member loginMember = findById(loginId);
        if(loginMember ==null)
            return null;
        if(loginMember.getPassword().equals(password))
            return loginMember;
        else/*널을 반환하면 아이디 또는 비번 잘못입력*/
            return null;
    }

    /*서버 시퀀스 아이디로 회원 조회*/
    @Override
    public Member findUser(Long id) {
        return store.get(id);
    }

    /*회원 아이디로 유저 조회*/
    public Member findById(String loginId){
        List<Member> memberList = getAll();
        for (Member member : memberList) {
            if(member.getLoginId().equals(loginId)){
                return member;
            }
        }
        return null;
    }

    @Override
    public Member addMemberFromDto(JoinReqDto joinReqDto) {
        return null;
    }

    /*모든 회원 조회*/
    @Override
    public List<Member> getAll() {
        return new ArrayList<>(store.values());
    }

    /*회원정보 변경*/
    @Override
    public Member changeUserInfo(Long loginId, EditMemberForm editedUser) {
//        Member member = findById(loginId);
//        member.changeUser(editedUser.getUsername(), editedUser.getEmail(), editedUser.getLoginId());
        return null;
    }

    @Override
    public void deleteUser(String loginId) {
        store.remove(loginId);
    }


//    @PostConstruct
//    public void initUser(){
//        Member user1 = new Member("찬영", "kinhchan223@gmail.com", "1111", "k");
//        Member user2 = new Member("상운", "sanhun@gmail.com", "1111", "sang112");
//        addUserBasic(user1);
//        addUserBasic(user2);
//    }
}
