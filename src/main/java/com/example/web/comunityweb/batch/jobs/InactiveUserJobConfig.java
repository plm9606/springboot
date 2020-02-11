package com.example.web.comunityweb.batch.jobs;

import com.example.web.comunityweb.batch.jobs.readers.QueueItemReader;
import com.example.web.comunityweb.domain.User;
import com.example.web.comunityweb.domain.enums.UserStatus;
import com.example.web.comunityweb.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@AllArgsConstructor
public class InactiveUserJobConfig {

    private UserRepository userRepository;

    @Bean
    public Job inactiveUserJob(JobBuilderFactory jobBuilderFactory, Step inactiveJobStep){
        return jobBuilderFactory.get("inactiveUserJob")
                // job의 재실행을 막는다.
                .preventRestart()
                .start(inactiveJobStep)
                .build();
    }

    @Bean
    public Step inactiveUserStep(StepBuilderFactory stepBuilderFactory){
        return stepBuilderFactory.get("inactiveUserStep")
                // 입출력의 타입을 모두 User로 설정. 커밋 단위는 10개로 설정
                .<User, User>chunk(10)
                .reader(inactiveUserReader())
                .processor(inactiveUserProcessor())
                .writer(inactiveUserWriter())
                .build();
    }


    @Bean
    @StepScope
    public QueueItemReader<User> inactiveUserReader(){
        List<User> oldUsers = userRepository.findByUpdatedDateBeforeAndStatusEquals(LocalDateTime.now().minusYears(1), UserStatus.ACTIVE);
        return  new QueueItemReader<>(oldUsers);
    }

    public ItemProcessor<User, User> inactiveUserProcessor(){
        return  new ItemProcessor<User, User>() {
            @Override
            public User process(User user) throws Exception {
                return user.setInactive();
            }
        };
//        return User::setInactive;
    }

    public ItemWriter<User> inactiveUserWriter(){
        return ((List<? extends User> users) -> userRepository.saveAll(users));
    }

}
