package com.community.service.interfaceService;

import com.community.domain.entity.User;
import com.community.domain.entity.formEntity.editUserForm;

import java.util.List;

public interface UserService {
    User addUser(String loginId, String userName, String email, String password);

    User findUser(String loginId);

    List<User> getAll();

    User addUserBasic(User user);

    User login(String loginId, String password);

    //아이디, 이름, 이메일 변경하기
    User changeUserInfo(String loginId, editUserForm newUser);
}
