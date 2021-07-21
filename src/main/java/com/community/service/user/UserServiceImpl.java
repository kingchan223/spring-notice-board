package com.community.service.user;


import com.community.domain.entity.User;
import com.community.domain.entity.formEntity.editUserForm;
import com.community.service.interfaceService.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Primary
@Slf4j
public class UserServiceImpl implements UserService {

    private Map<String, User> store = new ConcurrentHashMap<>();

    /*회원 저장*/
    public User addUser(String loginId, String username, String email, String password){
        User user = new User(username, email, password, loginId);
        store.put(loginId, user);
        return user;
    }

    /*회원 객체로 회원 저장*/
    public User addUserBasic(User user){
        store.put(user.getLoginId(), user);
        return user;
    }

    /*아이디, 패스워드로 로그인하기*/
    @Override
    public User login(String loginId, String password) {
        User loginUser = store.get(loginId);
        log.info("loginUser={}", loginUser);
        if(loginUser==null) return null;
        if(loginUser.getPassword().equals(password))
            return loginUser;
        else return null;
    }



    /*회원 조회*/
    @Override
    public User findUser(String loginId) {
        return store.get(loginId);
    }

    /*모든 회원 조회*/
    @Override
    public List<User> getAll() {
        return new ArrayList<>(store.values());
    }

    /*회원정보 변경*/
    @Override
    public User changeUserInfo(String loginId, editUserForm newUser) {
        log.info("loginId={}", loginId);
        User user = findUser(loginId);
        return user.changeUser(newUser);
    }


//    @PostConstruct
//    public void initUser(){
//        User user1 = new User("찬영", "kinhchan223@gmail.com", "1111", "k");
//        User user2 = new User("상운", "sanhun@gmail.com", "1111", "sang112");
//        addUserBasic(user1);
//        addUserBasic(user2);
//    }
}
