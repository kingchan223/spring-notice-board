package com.community.service.writing;


import com.community.domain.entity.Member;
import com.community.domain.entity.Writing;
import com.community.domain.entity.formEntity.AddWritingForm;
import com.community.domain.entity.formEntity.JoinMemberForm;
import com.community.repository.member.MemberRepository;
import com.community.repository.writing.WritingRepository;
import com.community.service.interfaceService.MemberService;
import com.community.service.interfaceService.WritingService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Primary
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class WritingServiceImpl implements WritingService {

    private final WritingRepository writingRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;//원래는 지우기

    @Transactional
    public Writing save(Long memberId, AddWritingForm writingForm){
        Member member = memberRepository.findOne(memberId);
        Writing writing = Writing.createWriting(member, writingForm);
        writingRepository.save(writing);
        return writing;
    }

    public Writing findOne(Long id){
        return writingRepository.findOne(id);
    }


    @Transactional
    @Override
    public void update(Long beforeWritingId, Writing afterWriting) {

    }

    @Transactional
    @Override
    public void delete(Long id) {
        writingRepository.delete(id);
    }

    public List<Writing> findAll(){
        return writingRepository.findAll();
    }


    @PostConstruct
    public void init(){
        JoinMemberForm joinMemberForm1 = new JoinMemberForm("k", "1111", "이찬영", "kingchan223@gmail.com", "seoul", "sanbon", "11111");
        JoinMemberForm joinMemberForm2 = new JoinMemberForm("l", "1111", "이상운", "sang@github.io.com", "seoul", "sanbon", "11111");
        Member initMember1 = memberService.addUser(joinMemberForm1);
        Member initMember2 = memberService.addUser(joinMemberForm2);
        AddWritingForm addWritingForm1 = new AddWritingForm("제목1입니다", "안녕하새요 DB적용중이에요");
        AddWritingForm addWritingForm2 = new AddWritingForm("네 저듀...~^^", "JPA적용하는데 오래걸리네요~");
        save(initMember1.getId(), addWritingForm1);
        save(initMember2.getId(), addWritingForm2);
    }
}
