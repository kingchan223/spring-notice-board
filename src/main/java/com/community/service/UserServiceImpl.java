package com.community.service;


import com.community.entity.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Primary
public class UserServiceImpl implements UserService{

    private Map<String, User> store = new ConcurrentHashMap<>();

    public User addUser(String loginId, String username, String email, String password){
        User user = new User(username, email, password, loginId);
        store.put(loginId, user);
        return user;
    }

    public User addUserBasic(User user){
        store.put(user.getLoginId(), user);
        return user;
    }

    @Override
    public User login(String loginId, String password) {
        User loginUser = store.get(loginId);
        if(loginUser.getPassword().equals(password))
            return loginUser;
        else return null;
    }


    @Override
    public User findUser(String loginId) {
        return store.get(loginId);
    }


    @Override
    public List<User> getAll() {
        return new ArrayList<>(store.values());
    }

    @PostConstruct
    public void initUser(){
        User user1 = new User("찬영", "kinhchan223@gmail.com", "1111", "kingchan223");
        User user2 = new User("상운", "sanhun@gmail.com", "1111", "sang112");
        addUserBasic(user1);
        addUserBasic(user2);
    }
}
