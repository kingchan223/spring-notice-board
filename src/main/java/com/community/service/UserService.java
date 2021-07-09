package com.community.service;

import com.community.entity.User;

import java.util.List;

public interface UserService {
    User addUser(String loginId, String userName, String email, String password);

    User findUser(String loginId);

    List<User> getAll();

    User addUserBasic(User user);

    User login(String loginId, String password);
}
