package com.example.web.comunityweb.repository;

import com.example.web.comunityweb.domain.Board;
import com.example.web.comunityweb.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByUser(User user);
}
