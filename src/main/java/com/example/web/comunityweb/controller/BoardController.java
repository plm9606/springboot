package com.example.web.comunityweb.controller;

import com.example.web.comunityweb.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    BoardService boardService;

    @GetMapping({"","/"})
    // @RequestParam 어노테이션을 사용하여 idx파라미터를 필수로 받는다.
    public String board(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model){
        model.addAttribute("board", boardService.findBoardByIdx(idx));
        return "/model/form";
    }

    @GetMapping("/list")
    // @PageableDefault 어노테이션의 size, sort, direction 등의 파라미터를 사용해 페이징 처리에 대한 규약을 정의할 수 있다.
    public String list(@PageableDefault Pageable pageable, Model model){
        model.addAttribute("boardList", boardService.findBoardList(pageable));

        // src/resources/templates를 기준으로 데이터를 바인딩할 타깃의 뷰 경로를 지정
        return "/board/list";

    }

}
