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
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@AllArgsConstructor
public class InactiveUserJobConfig {
    private final int CHUNK_SIZE = 15;

    private final EntityManagerFactory entityManagerFactory;
    private UserRepository userRepository;

    @Bean
    public Job inactiveUserJob(JobBuilderFactory jobBuilderFactory, Step inactiveJobStep){
        return jobBuilderFactory.get("inactiveUserJob")
                // job의 재실행을 막는다.
                .preventRestart()
                .start(inactiveJobStep)
                .build();
    }

//    @Bean
//    public Step inactiveUserStep(StepBuilderFactory stepBuilderFactory){
//        return stepBuilderFactory.get("inactiveUserStep")
//                // 입출력의 타입을 모두 User로 설정. 커밋 단위는 10개로 설정
//                .<User, User>chunk(10)
//                .reader(inactiveUserReader())
//                .processor(inactiveUserProcessor())
//                .writer(inactiveUserWriter())
//                .build();
//    }


    @Bean
    public Step inactiveUserPagingStep(StepBuilderFactory stepBuilderFactory, JpaPagingItemReader<User> inactiveUserJpaReader){
        return stepBuilderFactory.get("inactiveUserPagingStep")
                .<User,User>chunk(CHUNK_SIZE)
                .reader(inactiveUserJpaReader)  // 오잉 신기하게 함수로 실행하지 않고 파라미터로 받아서 넣어주는 이유는..?
                .processor(inactiveUserProcessor())
                .writer(inactiveUserJapWriter())
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

    // ListItemReader를 사용하면 모든 데이터를 한번에 가져와 메모리에 올려놓고 read()메서드로 하나씩 배치 작업을 한다.
    // 수십만개의 데이터를 가져와 처리해야할 때에는 어떡하지?
    // -> PagingItemReader구현체 사용한다. JdbcPagingItemReader, JpaPagingItemReader, Hibernate,,, 등등 존재함
    @Bean
    @StepScope
    public ListItemReader<User> inactiveUserReader2(){
        List<User> oldUsers = userRepository.findByUpdatedDateBeforeAndStatusEquals(LocalDateTime.now().minusYears(1), UserStatus.ACTIVE);
        return new ListItemReader<>(oldUsers);
    }

    // 스프링에서 destoryMethod를 사용해 삭제할 빈을 자동으로 추적한다.
    // destroyMethod = ""로 설정해 기능을 사용하지 않도록 설정한다.
    @Bean(destroyMethod = "")
    @StepScope
    public JpaPagingItemReader<User> inactiveUserJpaReader(){
        JpaPagingItemReader<User> jpaPagingItemReader = new JpaPagingItemReader(){
            @Override
            public int getPage() {
                return 0;
            }
        };
        // JpaPagingItemReader를 사용하기 위해서는 쿼리를 직접 짜는 방법밖에는 없다.
        jpaPagingItemReader.setQueryString("select u from User as u where u.updatedDate < :updatedDate and u.status = :status");

        Map<String, Object> map = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();
        map.put("updatedDate", now);
        map.put("status",UserStatus.ACTIVE);

        // 쿼리에서 사용된 updateDate, status 파라미터를 map에 추가해 사용할 파라미터를 설정한다.
        jpaPagingItemReader.setParameterValues(map);
        // 트랜잭션을 관리해줄 entityManangerFactory를 설정해준다.
        jpaPagingItemReader.setEntityManagerFactory(entityManagerFactory);
        // chunk 만큼 디비에서 데이터를 읽어오고 배치 작업을 한다.
        jpaPagingItemReader.setPageSize(CHUNK_SIZE);
        return jpaPagingItemReader;
    }

    public ItemWriter<User> inactiveUserWriter(){
        return ((List<? extends User> users) -> userRepository.saveAll(users));
    }


    // jpaItemWriter는 별도로 저장 설정을 할 필요 없이 제네릭에 저장할 타입을 명시하고 EntityManagerFactory만 설정하면
    // processor에서 넘어온 데이터를 청크 단위로 저장한다.
    public JpaItemWriter<User> inactiveUserJapWriter(){
        JpaItemWriter<User> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }

}
