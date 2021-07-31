package com.community.service.writing;

import com.community.domain.entity.Writing;
import com.community.domain.entity.formEntity.AddWritingForm;
import com.community.service.member.MemberService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
//@Service
public class MemoryWritingService implements WritingService {

    private final MemberService memberService;
    Map<Long, Writing> store = new ConcurrentHashMap<>();
    Long sequence = 1L;

    @Override
    public List<Writing> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Writing findOne(Long id) {
        return store.get(id);
    }

    @Override
    public Writing save(Long memberId, AddWritingForm writingForm){
//        writing.setId(sequence++);
//        store.put(writing.getId(), writing);
        return null;
    }

    @Override
    public void update(Long beforeWritingId, Writing afterWriting) {
        Writing findWriting = findOne(beforeWritingId);
        findWriting.setTitle(afterWriting.getTitle());
        findWriting.setContent(afterWriting.getContent());
    }

    @Override
    public void delete(Long id) {
        store.remove(id);
    }


}
