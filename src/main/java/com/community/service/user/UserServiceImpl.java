package com.community.service.user;


import com.community.domain.entity.User;
import com.community.domain.entity.formEntity.EditUserForm;
import com.community.domain.entity.formEntity.JoinUserForm;
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

    private Map<Long, User> store = new ConcurrentHashMap<>();
    private Long squence = 0L;

    /*회원 저장*/
    public User addUser(JoinUserForm joinUserForm){
        Long id = squence++;
        User user = new User(joinUserForm.getUsername(), joinUserForm.getEmail(), joinUserForm.getPassword(), joinUserForm.getLoginId(), id);
        store.put(id, user);
        return user;
    }

    /*회원 객체로 회원 저장*/
    public User addUserBasic(User user){
        store.put(user.getId(), user);
        return user;
    }

    /*아이디, 패스워드로 로그인하기*/
    @Override
    public User login(String loginId, String password) {
        User loginUser = findById(loginId);
        if(loginUser==null)
            return null;
        if(loginUser.getPassword().equals(password))
            return loginUser;
        else/*널을 반환하면 아이디 또는 비번 잘못입력*/
            return null;
    }

    /*서버 시퀀스 아이디로 회원 조회*/
    @Override
    public User findUser(Long id) {
        return store.get(id);
    }

    /*회원 아이디로 유저 조회*/
    public User findById(String loginId){
        List<User> userList = getAll();
        for (User user : userList) {
            if(user.getLoginId().equals(loginId)){
                return user;
            }
        }
        return null;
    }

    /*모든 회원 조회*/
    @Override
    public List<User> getAll() {
        return new ArrayList<>(store.values());
    }

    /*회원정보 변경*/
    @Override
    public void changeUserInfo(String loginId, EditUserForm editedUser) {
        User user = findById(loginId);
        user.changeUser(editedUser.getUsername(), editedUser.getEmail(), editedUser.getLoginId());
    }

    @Override
    public void deleteUser(String loginId) {
        store.remove(loginId);
    }


//    @PostConstruct
//    public void initUser(){
//        User user1 = new User("찬영", "kinhchan223@gmail.com", "1111", "k");
//        User user2 = new User("상운", "sanhun@gmail.com", "1111", "sang112");
//        addUserBasic(user1);
//        addUserBasic(user2);
//    }
}
