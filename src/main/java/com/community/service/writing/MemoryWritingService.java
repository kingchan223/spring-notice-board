package com.community.service.writing;

import com.community.domain.entity.User;
import com.community.domain.entity.Writing;
import com.community.domain.entity.formEntity.JoinUserForm;
import com.community.service.interfaceService.UserService;
import com.community.service.interfaceService.WritingService;
import lombok.RequiredArgsConstructor;
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
    public Writing add(Writing writing){
        writing.setId(sequence++);
        store.put(writing.getId(), writing);
        return writing;
    }

    @Override
    public void update(Long beforeWritingId, Writing afterWriting) {
        Writing findWriting = getOne(beforeWritingId);
        findWriting.setTitle(afterWriting.getTitle());
        findWriting.setContent(afterWriting.getContent());
    }

    @Override
    public void delete(Long id) {
        store.remove(id);
    }

    @PostConstruct
    public void init(){
        JoinUserForm JoinUserForm1 = new JoinUserForm("k", "1111", "이찬영", "kingchan223@gmail.com");
        JoinUserForm JoinUserForm2 = new JoinUserForm("l", "1111", "이상운", "sang@github.io.com");
        User initUser1 = userService.addUser(JoinUserForm1);
        User initUser2 = userService.addUser(JoinUserForm2);

        Writing writing1 = new Writing("안녕하세요", "하하하", initUser1, null);
        Writing writing2 = new Writing("하이~", "하하하", initUser2, null);
        add(writing1);
        add(writing2);
    }
}
