package com.community.service.interfaceService;

import com.community.domain.entity.User;
import com.community.domain.entity.formEntity.EditUserForm;
import com.community.domain.entity.formEntity.JoinUserForm;

import java.util.List;

public interface UserService {
    User addUser(JoinUserForm joinUserForm);

    User findUser(Long id);

    List<User> getAll();

    User addUserBasic(User user);

    User login(String loginId, String password);

    //아이디, 이름, 이메일 변경하기
    void changeUserInfo(String loginId, EditUserForm newUser);

    void deleteUser(String loginId);

    User findById(String loginId);
}
