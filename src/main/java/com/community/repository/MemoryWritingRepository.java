package com.community.repository;

import com.community.entity.Writing;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryWritingRepository implements WritingRepository{

    Map<Long, Writing> store = new ConcurrentHashMap<>();
    Long sequence = 1L;

    @Override
    public List<Writing> getAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Writing getOne(Long id) {
        return store.get(id);
    }

    @Override
    public Writing add(Writing writing) {
        writing.setId(sequence++);
        store.put(writing.getId(), writing);
        return writing;
    }

    @Override
    public void update(Writing writing) {
        Writing findWriting = getOne(writing.getId());
        findWriting.setTitle(writing.getTitle());
        findWriting.setContent(writing.getContent());
    }

    @Override
    public void delete(Long id) {
        store.remove(id);
    }

    @PostConstruct
    public void init(){
        Writing writing1 = new Writing("안녕하세요", "하하하");
        Writing writing2 = new Writing("하이~", "하하하");
        add(writing1);
        add(writing2);
    }
}
