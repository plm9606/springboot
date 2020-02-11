package com.example.web.comunityweb.repository;

import com.example.web.comunityweb.domain.Board;
import com.example.web.comunityweb.domain.User;
import com.example.web.comunityweb.domain.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    List<User> findByUpdatedDateBeforeAndStatusEquals(LocalDateTime localDateTime, UserStatus status);
}
