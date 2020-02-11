package com.example.web.comunityweb;

import com.example.web.comunityweb.domain.Board;
import com.example.web.comunityweb.domain.User;
import com.example.web.comunityweb.domain.enums.BoardType;
import com.example.web.comunityweb.repository.BoardRepository;
import com.example.web.comunityweb.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaMappingTest {
    private String boardTestTitle = "테스트";
    private String email = "test@gmail.com";

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @Before
    public void 제대로_생성됐는지_테스트(){
        User user = userRepository.save(User.builder()
                .name("havi")
                .password("test")
                .email(email)
                .createdDate(LocalDateTime.now())
                .build());

        boardRepository.save(Board.builder()
                .title(boardTestTitle)
                .subTitle("sub")
                .content("콘텐츠")
                .boardType(BoardType.free)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .user(user)
                .build());
    }

    @Test
    public void testRepository(){
        User user = userRepository.findByEmail(email);
        assertThat(user.getName(), is("havi"));
        assertThat(user.getPassword(), is("test"));
        assertThat(user.getEmail(), is(email));

        Board board = boardRepository.findByUser(user);
        assertThat(board.getBoardType(),is(BoardType.free));
        assertThat(board.getTitle(),is(boardTestTitle));
    }
}
