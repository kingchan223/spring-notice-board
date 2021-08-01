package com.community.service.writing;


import com.community.domain.entity.Member;
import com.community.domain.entity.Writing;
import com.community.domain.entity.formEntity.AddWritingForm;
import com.community.repository.member.MemberRepository;
import com.community.repository.writing.WritingRepository;
import com.community.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
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
    public Writing save(Long memberId, AddWritingForm writingForm) throws IOException {
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

}
