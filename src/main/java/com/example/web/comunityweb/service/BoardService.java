package com.example.web.comunityweb.service;

import com.example.web.comunityweb.domain.Board;
import com.example.web.comunityweb.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    public Page<Board> findBoardList(Pageable pageable){
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0: pageable.getPageNumber() -1, pageable.getPageSize());

        Page<Board> list = boardRepository.findAll(pageable);
        return boardRepository.findAll(pageable);
    }


    public Board findBoardByIdx(Long idx){
        // board의 idx값을 사용해 board객체 반환
        return boardRepository.findById(idx).orElse(new Board());
    }
}
