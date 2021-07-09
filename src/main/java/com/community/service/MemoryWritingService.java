package com.community.service;

import com.community.entity.User;
import com.community.entity.Writing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class MemoryWritingService implements WritingService {

    private final UserService userService;
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
        User user1 = new User("찬영", "kinhchan223@gmail.com", "1111", "k");
        User user2 = new User("상운", "sanhun@gmail.com", "1111", "sang112");
        userService.addUserBasic(user1);
        userService.addUserBasic(user2);

        Writing writing1 = new Writing("안녕하세요", "하하하", user1);
        Writing writing2 = new Writing("하이~", "하하하", user2);
        add(writing1);
        add(writing2);
    }
}
