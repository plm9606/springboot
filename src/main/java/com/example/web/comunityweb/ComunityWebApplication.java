package com.example.web.comunityweb;

import com.example.web.comunityweb.domain.Board;
import com.example.web.comunityweb.domain.User;
import com.example.web.comunityweb.domain.enums.BoardType;
import com.example.web.comunityweb.domain.enums.UserStatus;
import com.example.web.comunityweb.repository.BoardRepository;
import com.example.web.comunityweb.repository.UserRepository;
import com.sun.tools.javah.Gen;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.*;
import java.util.stream.IntStream;

@SpringBootApplication
@EnableBatchProcessing
public class ComunityWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComunityWebApplication.class, args);
    }

    @Bean
    // CommandLineRunner를 빈을 등록 후 repository를 주입받는다.
    public CommandLineRunner runner(UserRepository userRepository, BoardRepository boardRepository) throws Exception{
        return (args -> {
            // user 객체를 Builder Pattern으로 생성한 후 주입받은 userRepository를 사용해 객체를 저장
           User user = userRepository.save(User.builder()
                   .name("havi")
                   .password("test")
                   .email("test@gmail.com")
                   .createdDate(LocalDateTime.now())
                   .build());

           IntStream.rangeClosed(1,100).forEach(index->
               userRepository.save(User.builder()
                       .name("user"+index)
                       .password("test")
                       .email("user"+index+"@gmail.com")
                       .createdDate(LocalDateTime.now().minusYears(3))
                       .updatedDate(index%2==0?LocalDateTime.now():LocalDateTime.now().minusYears(2))
                       .status(UserStatus.ACTIVE)
                       .build()));

           // 빌더 패턴을 사용해 Board객체 저장
            // IntStream의 rangeClosed를 사용하여 index순서대로 Board객체를 200개 생성하여 저장.
            IntStream.rangeClosed(1,200).forEach(index->
                    boardRepository.save(Board.builder()
                            .title("게시글"+index)
                            .subTitle("순서"+index)
                            .content("콘텐츠")
                            .boardType(BoardType.free)
                            .createdDate(LocalDateTime.now())
                            .updatedDate(LocalDateTime.now())
                            .user(user)
                            .build()
                    ));
        });
    }

}
