package com.community.repository;


import com.community.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email); // 이미 가입한 사람인지 아닌지를 판단하기 위해서

}